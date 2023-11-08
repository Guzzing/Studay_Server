package org.guzzing.studayserver.domain.dashboard.controller;

import org.guzzing.studayserver.domain.dashboard.service.DashboardService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/dashboards")
public class DashboardRestController {

    private final DashboardService dashboardService;

    public DashboardRestController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }
}
