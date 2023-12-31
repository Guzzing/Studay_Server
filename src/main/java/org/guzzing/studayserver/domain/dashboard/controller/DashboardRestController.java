package org.guzzing.studayserver.domain.dashboard.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import jakarta.validation.Valid;
import org.guzzing.studayserver.domain.auth.memberId.MemberId;
import org.guzzing.studayserver.domain.dashboard.controller.converter.DashboardControllerConverter;
import org.guzzing.studayserver.domain.dashboard.controller.dto.request.DashboardPostRequest;
import org.guzzing.studayserver.domain.dashboard.controller.dto.request.DashboardPutRequest;
import org.guzzing.studayserver.domain.dashboard.controller.dto.response.DashboardGetResponse;
import org.guzzing.studayserver.domain.dashboard.controller.dto.response.DashboardGetResponses;
import org.guzzing.studayserver.domain.dashboard.controller.dto.response.DashboardPatchResponse;
import org.guzzing.studayserver.domain.dashboard.controller.dto.response.DashboardPostResponse;
import org.guzzing.studayserver.domain.dashboard.controller.dto.response.DashboardPutResponse;
import org.guzzing.studayserver.domain.dashboard.facade.DashboardFacade;
import org.guzzing.studayserver.domain.dashboard.facade.dto.DashboardGetResult;
import org.guzzing.studayserver.domain.dashboard.facade.dto.DashboardGetResults;
import org.guzzing.studayserver.domain.dashboard.facade.dto.DashboardPatchResult;
import org.guzzing.studayserver.domain.dashboard.facade.dto.DashboardPostResult;
import org.guzzing.studayserver.domain.dashboard.facade.dto.DashboardPutResult;
import org.guzzing.studayserver.domain.dashboard.service.dto.request.DashboardPostParam;
import org.guzzing.studayserver.domain.dashboard.service.dto.request.DashboardPutParam;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/dashboards")
public class DashboardRestController {

    private final DashboardControllerConverter controllerConverter;
    private final DashboardFacade dashboardFacade;

    public DashboardRestController(
            final DashboardControllerConverter controllerConverter,
            final DashboardFacade dashboardFacade
    ) {
        this.controllerConverter = controllerConverter;
        this.dashboardFacade = dashboardFacade;
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
            @Valid @RequestBody final DashboardPostRequest request,
            @MemberId final Long memberId
    ) {
        final DashboardPostParam param = controllerConverter.to(request);
        final DashboardPostResult result = dashboardFacade.createDashboard(param, memberId);
        final DashboardPostResponse response = controllerConverter.from(result);

        return ResponseEntity
                .status(CREATED)
                .body(response);
    }

    /**
     * 비활성화된 대시보드 제거
     *
     * @param dashboardId
     * @param memberId
     * @return Void
     */
    @PatchMapping(path = "/{dashboardId}")
    public ResponseEntity<Void> removeDashboard(
            @PathVariable final Long dashboardId,
            @MemberId final Long memberId
    ) {
        dashboardFacade.removeDashboard(dashboardId, memberId);

        return ResponseEntity
                .status(NO_CONTENT)
                .build();
    }

    /**
     * 대시보드 수정
     *
     * @param dashboardId
     * @param request
     * @param memberId
     * @return DashboardPutResponse
     */
    @PutMapping(path = "/{dashboardId}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<DashboardPutResponse> updateDashboard(
            @PathVariable final Long dashboardId,
            @Valid @RequestBody final DashboardPutRequest request,
            @MemberId final Long memberId
    ) {
        final DashboardPutParam param = controllerConverter.to(dashboardId, request);
        final DashboardPutResult result = dashboardFacade.modifyDashboard(param, memberId);
        final DashboardPutResponse response = controllerConverter.from(result);

        return ResponseEntity
                .status(OK)
                .body(response);
    }

    /**
     * 대시보드 활성화 여부 반전
     *
     * @param dashboardId
     * @param memberId
     * @return DashboardPatchResponse
     */
    @PatchMapping(path = "/{dashboardId}/toggle", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<DashboardPatchResponse> revertActiveOfDashboard(
            @PathVariable final Long dashboardId,
            @MemberId final Long memberId
    ) {
        final DashboardPatchResult result = dashboardFacade.revertDashboardActiveness(dashboardId, memberId);
        final DashboardPatchResponse response = controllerConverter.from(result);

        return ResponseEntity
                .status(OK)
                .body(response);
    }

    /**
     * 대시보드 단건 조회
     *
     * @param dashboardId
     * @param memberId
     * @return DashboardGetResponse
     */
    @GetMapping(path = "/{dashboardId}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<DashboardGetResponse> getDashboard(
            @PathVariable final Long dashboardId,
            @MemberId final Long memberId
    ) {
        final DashboardGetResult result = dashboardFacade.getDashboard(dashboardId, memberId);
        final DashboardGetResponse response = controllerConverter.from(result);

        return ResponseEntity
                .status(OK)
                .body(response);
    }

    /**
     * 활성화 여부에 따른 대시보드 전체 조회
     *
     * @param childId
     * @param activeOnly
     * @param memberId
     * @return DashboardGetResponses
     */
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<DashboardGetResponses> getDashboards(
            @RequestParam final Long childId,
            @RequestParam(
                    name = "active-only",
                    required = false,
                    defaultValue = "false") final Boolean activeOnly,
            @MemberId final Long memberId
    ) {
        final DashboardGetResults results = dashboardFacade.getDashboards(childId, activeOnly, memberId);
        final DashboardGetResponses responses = controllerConverter.from(results);

        return ResponseEntity
                .status(OK)
                .body(responses);
    }

}
