package org.guzzing.studayserver.domain.dashboard.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import org.guzzing.studayserver.domain.auth.memberId.MemberId;
import org.guzzing.studayserver.domain.dashboard.controller.converter.DashboardControllerConverter;
import org.guzzing.studayserver.domain.dashboard.controller.dto.request.DashboardPostRequest;
import org.guzzing.studayserver.domain.dashboard.controller.dto.response.DashboardGetResponse;
import org.guzzing.studayserver.domain.dashboard.controller.dto.response.DashboardPostResponse;
import org.guzzing.studayserver.domain.dashboard.service.DashboardService;
import org.guzzing.studayserver.domain.dashboard.service.dto.request.DashboardPostParam;
import org.guzzing.studayserver.domain.dashboard.service.dto.response.DashboardGetResult;
import org.guzzing.studayserver.domain.dashboard.service.dto.response.DashboardPostResult;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/dashboards")
public class DashboardRestController {

    private final DashboardControllerConverter controllerConverter;
    private final DashboardService dashboardService;

    public DashboardRestController(
            final DashboardControllerConverter controllerConverter,
            final DashboardService dashboardService
    ) {
        this.controllerConverter = controllerConverter;
        this.dashboardService = dashboardService;
    }

    /**
     * 대시보드 등록
     *
     * @param request
     * @param memberId
     * @return DashboardPostResponse
     */
    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<DashboardPostResponse> registerDashboard(
            @Validated @RequestBody final DashboardPostRequest request,
            @MemberId final Long memberId
    ) {
        final DashboardPostParam param = controllerConverter.to(request);
        final DashboardPostResult result = dashboardService.createDashboard(param, memberId);
        final DashboardPostResponse response = controllerConverter.from(result);

        return ResponseEntity
                .status(CREATED)
                .body(response);
    }

    @GetMapping(path = "/{dashboardId}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<DashboardGetResponse> getDashboard(
            @PathVariable final Long dashboardId,
            @MemberId final Long memberId
    ) {
        final DashboardGetResult result = dashboardService.findDashboard(dashboardId, memberId);
        final DashboardGetResponse response = controllerConverter.from(result);

        return ResponseEntity
                .status(OK)
                .body(response);
    }

}
