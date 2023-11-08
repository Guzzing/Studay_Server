package org.guzzing.studayserver.domain.dashboard.service;

import org.guzzing.studayserver.domain.dashboard.model.Dashboard;
import org.guzzing.studayserver.domain.dashboard.repository.DashboardRepository;
import org.guzzing.studayserver.domain.dashboard.service.dto.request.DashboardPostParam;
import org.guzzing.studayserver.domain.dashboard.service.dto.response.DashboardResult;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {

    private final DashboardRepository dashboardRepository;

    public DashboardService(final DashboardRepository dashboardRepository) {
        this.dashboardRepository = dashboardRepository;
    }

    public void createDashboard(final DashboardPostParam param) {
        Dashboard dashboard = DashboardPostParam.to(param);

        Dashboard savedDashboard = dashboardRepository.save(dashboard);

        return DashboardResult.from(savedDashboard);
    }
}
