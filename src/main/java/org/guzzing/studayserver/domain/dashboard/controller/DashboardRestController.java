package org.guzzing.studayserver.domain.dashboard.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import org.guzzing.studayserver.domain.auth.memberId.MemberId;
import org.guzzing.studayserver.domain.dashboard.controller.converter.DashboardControllerConverter;
import org.guzzing.studayserver.domain.dashboard.controller.dto.request.DashboardPostRequest;
import org.guzzing.studayserver.domain.dashboard.controller.dto.response.DashboardGetResponses;
import org.guzzing.studayserver.domain.dashboard.controller.dto.response.DashboardPostResponse;
import org.guzzing.studayserver.domain.dashboard.service.DashboardService;
import org.guzzing.studayserver.domain.dashboard.service.dto.request.DashboardPostParam;
import org.guzzing.studayserver.domain.dashboard.service.dto.response.DashboardResult;
import org.guzzing.studayserver.domain.dashboard.service.dto.response.DashboardResults;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
        final DashboardResult result = dashboardService.createDashboard(param, memberId);
        final DashboardPostResponse response = controllerConverter.postResponseFromResult(result);

        return ResponseEntity
                .status(CREATED)
                .body(response);
    }

    /**
     * 대시보드 활성화 여부에 따라서 아이의 대시보드 조회
     *
     * @param childId
     * @param activeOnly
     * @param memberId
     * @return DashboardGetResponses
     */
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<DashboardGetResponses> getDashboard(
            @RequestParam final Long childId,
            @RequestParam(name = "active-only", defaultValue = "false") final Boolean activeOnly,
            @MemberId final Long memberId
    ) {
        final DashboardResults results = dashboardService.findDashboardOfChild(childId, activeOnly, memberId);
        final DashboardGetResponses responses = controllerConverter.getResponsesFromResults(results);

        return ResponseEntity
                .status(OK)
                .body(responses);
    }
}
