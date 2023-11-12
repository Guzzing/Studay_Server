package org.guzzing.studayserver.domain.dashboard.service;

import org.guzzing.studayserver.domain.dashboard.model.Dashboard;
import org.guzzing.studayserver.domain.dashboard.repository.DashboardRepository;
import org.guzzing.studayserver.domain.dashboard.service.converter.DashboardServiceConverter;
import org.guzzing.studayserver.domain.dashboard.service.dto.request.DashboardPostParam;
import org.guzzing.studayserver.domain.dashboard.service.dto.response.DashboardResult;
import org.guzzing.studayserver.domain.member.service.MemberAccessService;
import org.guzzing.studayserver.global.exception.DashboardException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class DashboardService {

    private final DashboardServiceConverter serviceConverter;
    private final DashboardRepository dashboardRepository;
    private final MemberAccessService memberAccessService;

    public DashboardService(
            final DashboardServiceConverter serviceConverter,
            final DashboardRepository dashboardRepository,
            final MemberAccessService memberAccessService
    ) {
        this.serviceConverter = serviceConverter;
        this.dashboardRepository = dashboardRepository;
        this.memberAccessService = memberAccessService;
    }

    @Transactional
    public DashboardResult createDashboard(final DashboardPostParam param) {
        final Dashboard dashboard = serviceConverter.to(param);
        final Dashboard savedDashboard = dashboardRepository.save(dashboard);

        return serviceConverter.from(param.academyId(), savedDashboard);
    }
}
