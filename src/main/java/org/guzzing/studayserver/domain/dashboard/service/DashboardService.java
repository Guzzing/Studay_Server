package org.guzzing.studayserver.domain.dashboard.service;

import jakarta.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.guzzing.studayserver.domain.dashboard.model.Dashboard;
import org.guzzing.studayserver.domain.dashboard.model.DashboardSchedule;
import org.guzzing.studayserver.domain.dashboard.model.vo.FeeInfo;
import org.guzzing.studayserver.domain.dashboard.repository.DashboardRepository;
import org.guzzing.studayserver.domain.dashboard.repository.DashboardScheduleJpaRepository;
import org.guzzing.studayserver.domain.dashboard.service.converter.DashboardServiceConverter;
import org.guzzing.studayserver.domain.dashboard.service.dto.request.DashboardPostParam;
import org.guzzing.studayserver.domain.dashboard.service.dto.request.DashboardPutParam;
import org.guzzing.studayserver.domain.dashboard.service.dto.response.DashboardResult;
import org.guzzing.studayserver.domain.dashboard.service.overlap.DashboardScheduleOverlapChecker;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class DashboardService {

    private final DashboardServiceConverter serviceConverter;
    private final DashboardRepository dashboardRepository;
    private final DashboardScheduleJpaRepository dashboardScheduleJpaRepository;
    private final DashboardScheduleOverlapChecker dashboardScheduleOverlapChecker;

    public DashboardService(
            final DashboardServiceConverter serviceConverter,
            final DashboardRepository dashboardRepository,
            final DashboardScheduleJpaRepository dashboardScheduleJpaRepository,
            final DashboardScheduleOverlapChecker dashboardScheduleOverlapChecker) {
        this.serviceConverter = serviceConverter;
        this.dashboardRepository = dashboardRepository;
        this.dashboardScheduleJpaRepository = dashboardScheduleJpaRepository;
        this.dashboardScheduleOverlapChecker = dashboardScheduleOverlapChecker;
    }

    @Transactional
    public DashboardResult saveDashboard(final DashboardPostParam param) {
        final Dashboard dashboard = serviceConverter.to(param);
        final Dashboard savedDashboard = dashboardRepository.save(dashboard);

        return serviceConverter.from(savedDashboard);
    }

    @Transactional
    public void deleteDashboard(final long dashboardId) {
        this.findDashboardById(dashboardId)
                .delete();
    }

    @Transactional
    public void removeDashboard(final List<Long> childIds) {
        dashboardScheduleJpaRepository.deleteByChildIds(childIds);
        dashboardRepository.deleteByChildIds(childIds);
    }

    @Transactional
    public DashboardResult editDashboard(final DashboardPutParam param) {
        final FeeInfo feeInfo = serviceConverter.convertToFeeInfo(param.paymentInfo());
        final Map<String, Boolean> simpleMemo = serviceConverter.convertToSimpleMemoMap(param.simpleMemo());

        final Dashboard dashboard = dashboardRepository.findDashboardById(param.dashboardId())
                .updateFeeInfo(feeInfo)
                .updateSimpleMemo(simpleMemo);

        return serviceConverter.from(dashboard);
    }

    @Transactional
    public DashboardResult toggleDashboardActiveness(final long dashboardId) {
        final Dashboard dashboard = dashboardRepository.findDashboardById(dashboardId);

        final List<DashboardSchedule> childDashboardSchedules = dashboardRepository.findActiveOnlyByChildId(
                        dashboard.getChildId())
                .stream()
                .filter(childDashboard -> !Objects.equals(dashboard.getId(), childDashboard.getId()))
                .map(Dashboard::getDashboardSchedules)
                .flatMap(List::stream)
                .collect(Collectors.toCollection(() -> Collections.synchronizedList(new ArrayList<>())));

        dashboardScheduleOverlapChecker.checkScheduleOverlap(dashboard.getDashboardSchedules(),
                childDashboardSchedules);

        dashboard.toggleActive();

        return serviceConverter.from(dashboard);
    }

    public DashboardResult findDashboard(final long dashboardId) {
        final Dashboard dashboard = this.findDashboardById(dashboardId);

        return serviceConverter.from(dashboard);
    }

    public List<DashboardResult> findDashboards(final long childId, final boolean activeOnly) {
        final List<Dashboard> dashboards = activeOnly
                ? dashboardRepository.findActiveOnlyByChildId(childId)
                : dashboardRepository.findAllByChildId(childId);

        return dashboards.stream()
                .map(serviceConverter::from)
                .toList();
    }

    private Dashboard findDashboardById(final Long dashboardId) {
        return dashboardRepository.findById(dashboardId)
                .orElseThrow(() -> new EntityNotFoundException("존재하는 대시보드가 없습니다."));
    }

}
