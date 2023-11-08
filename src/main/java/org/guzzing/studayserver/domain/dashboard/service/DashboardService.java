package org.guzzing.studayserver.domain.dashboard.service;

import org.guzzing.studayserver.domain.dashboard.repository.DashboardRepository;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {

    private final DashboardRepository dashboardRepository;

    public DashboardService(DashboardRepository dashboardRepository) {
        this.dashboardRepository = dashboardRepository;
    }
}
