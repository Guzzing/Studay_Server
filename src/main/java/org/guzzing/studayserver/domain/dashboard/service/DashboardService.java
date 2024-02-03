package org.guzzing.studayserver.domain.dashboard.service;

import jakarta.persistence.EntityNotFoundException;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import org.guzzing.studayserver.domain.calendar.model.Periodicity;
import org.guzzing.studayserver.domain.dashboard.model.Dashboard;
import org.guzzing.studayserver.domain.dashboard.model.vo.FeeInfo;
import org.guzzing.studayserver.domain.dashboard.repository.DashboardRepository;
import org.guzzing.studayserver.domain.dashboard.repository.DashboardScheduleJpaRepository;
import org.guzzing.studayserver.domain.dashboard.service.access.dto.LessonScheduleAccessResult;
import org.guzzing.studayserver.domain.dashboard.service.converter.DashboardServiceConverter;
import org.guzzing.studayserver.domain.dashboard.service.dto.request.DashboardPostParam;
import org.guzzing.studayserver.domain.dashboard.service.dto.request.DashboardPutParam;
import org.guzzing.studayserver.domain.dashboard.service.dto.response.DashboardResult;
import org.guzzing.studayserver.domain.dashboard.service.dto.response.DashboardScheduleAccessResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class DashboardService {

    private static final String SEPARATOR = ":";
    private final DashboardServiceConverter serviceConverter;
    private final DashboardRepository dashboardRepository;
    private final DashboardScheduleJpaRepository dashboardScheduleJpaRepository;

    public DashboardService(
            final DashboardServiceConverter serviceConverter,
            final DashboardRepository dashboardRepository,
            final DashboardScheduleJpaRepository dashboardScheduleJpaRepository) {
        this.serviceConverter = serviceConverter;
        this.dashboardRepository = dashboardRepository;
        this.dashboardScheduleJpaRepository = dashboardScheduleJpaRepository;
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
        final Dashboard dashboard = dashboardRepository.findDashboardById(dashboardId)
                .toggleActive();

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

    @Transactional(readOnly = true)
    public DashboardScheduleAccessResult getDashboardSchedule(Long dashboardId) {
        final DashboardResult result = findDashboard(dashboardId);

        return new DashboardScheduleAccessResult(
                result.childId(),
                result.academyId(),
                result.lessonId());
    }

    private LocalTime getTime(final String time) {
        String[] split = time.split(SEPARATOR);
        try {
            int hour = Integer.parseInt(split[0]);
            int minute = Integer.parseInt(split[1]);

            return LocalTime.of(hour, minute);

        } catch (ArrayIndexOutOfBoundsException e) {
            throw new IllegalArgumentException("유효하지 않은 시간입니다.");
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("시간은 숫자여야 합니다.");
        }
    }

}
