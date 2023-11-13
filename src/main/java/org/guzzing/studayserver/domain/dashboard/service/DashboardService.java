package org.guzzing.studayserver.domain.dashboard.service;

import java.util.List;
import org.guzzing.studayserver.domain.academy.service.AcademyAccessService;
import org.guzzing.studayserver.domain.dashboard.model.Dashboard;
import org.guzzing.studayserver.domain.dashboard.repository.DashboardRepository;
import org.guzzing.studayserver.domain.dashboard.service.converter.DashboardServiceConverter;
import org.guzzing.studayserver.domain.dashboard.service.dto.request.DashboardPostParam;
import org.guzzing.studayserver.domain.dashboard.service.dto.response.DashboardResult;
import org.guzzing.studayserver.domain.dashboard.service.dto.response.DashboardResults;
import org.guzzing.studayserver.domain.member.service.MemberAccessService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class DashboardService {

    private final DashboardServiceConverter serviceConverter;
    private final DashboardRepository dashboardRepository;
    private final MemberAccessService memberAccessService;
    private final AcademyAccessService academyAccessService;

    public DashboardService(
            final DashboardServiceConverter serviceConverter,
            final DashboardRepository dashboardRepository,
            final MemberAccessService memberAccessService,
            final AcademyAccessService academyAccessService
    ) {
        this.serviceConverter = serviceConverter;
        this.dashboardRepository = dashboardRepository;
        this.memberAccessService = memberAccessService;
        this.academyAccessService = academyAccessService;
    }

    @Transactional
    public DashboardResult createDashboard(final DashboardPostParam param, final Long memberId) {
        memberAccessService.validateMember(memberId);
        academyAccessService.validateAcademy(param.academyId());
        academyAccessService.validateLesson(param.lessonId());

        final Dashboard dashboard = serviceConverter.to(param);
        final Dashboard savedDashboard = dashboardRepository.save(dashboard);

        return serviceConverter.from(savedDashboard);
    }

    public DashboardResults findDashboardOfChild(
            final long childId,
            final boolean activeOnly,
            final long memberId
    ) {
        memberAccessService.validateMember(memberId);

        if (activeOnly) {
            final List<Dashboard> dashboards = dashboardRepository.findActiveOnlyByChildId(childId);

            return serviceConverter.from(dashboards);
        }

        final List<Dashboard> dashboards = dashboardRepository.findAllByChildId(childId);

        return serviceConverter.from(dashboards);
    }
}
