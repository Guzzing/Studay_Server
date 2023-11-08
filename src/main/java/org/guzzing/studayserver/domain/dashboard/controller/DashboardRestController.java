package org.guzzing.studayserver.domain.dashboard.controller;

import static org.springframework.http.HttpStatus.CREATED;

import org.guzzing.studayserver.domain.dashboard.controller.dto.request.DashboardPostRequest;
import org.guzzing.studayserver.domain.dashboard.service.DashboardService;
import org.guzzing.studayserver.domain.dashboard.service.dto.request.DashboardPostParam;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/dashboards")
public class DashboardRestController {

    private final DashboardService dashboardService;

    public DashboardRestController(final DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @PostMapping
    public ResponseEntity<String> registerDashBoard(
            @Validated @RequestBody DashboardPostRequest request
    ) {
        DashboardPostParam param = DashboardPostRequest.to(request);
        dashboardService.createDashboard(param);

        return ResponseEntity
                .status(CREATED)
                .body(param.toString());
    }
}
