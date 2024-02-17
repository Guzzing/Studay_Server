package org.guzzing.studayserver.domain.academy.controller;

import jakarta.validation.Valid;
import org.guzzing.studayserver.domain.academy.controller.dto.request.AcademiesByNameRequest;
import org.guzzing.studayserver.domain.academy.controller.dto.request.AcademyByLocationWithCursorRequest;
import org.guzzing.studayserver.domain.academy.controller.dto.request.AcademyByLocationWithScrollRequest;
import org.guzzing.studayserver.domain.academy.controller.dto.request.AcademyFilterWithScrollRequest;
import org.guzzing.studayserver.domain.academy.controller.dto.response.*;
import org.guzzing.studayserver.domain.academy.facade.AcademyFacade;
import org.guzzing.studayserver.domain.academy.facade.dto.AcademyDetailFacadeParam;
import org.guzzing.studayserver.domain.academy.facade.dto.AcademyDetailFacadeResult;
import org.guzzing.studayserver.domain.academy.service.AcademyService;
import org.guzzing.studayserver.domain.academy.service.dto.result.*;
import org.guzzing.studayserver.global.common.member.MemberId;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/academies")
public class AcademyController {

    private final AcademyService academyService;
    private final AcademyFacade academyFacade;

    public AcademyController(AcademyService academyService, AcademyFacade academyFacade) {
        this.academyService = academyService;
        this.academyFacade = academyFacade;
    }

    @GetMapping(
            path = "/{academyId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AcademyGetResponse> getAcademy(
            @PathVariable Long academyId,
            @MemberId Long memberId
    ) {
        AcademyDetailFacadeResult detailAcademy = academyFacade.getDetailAcademy(
                AcademyDetailFacadeParam.of(memberId, academyId));

        return ResponseEntity.status(HttpStatus.OK)
                .body(AcademyGetResponse.from(detailAcademy));
    }

    @GetMapping(
            path = "/complexes-scroll",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AcademiesByLocationWithScrollResponses> findByLocationWithScroll(
            @ModelAttribute @Valid AcademyByLocationWithScrollRequest request,
            @MemberId Long memberId
    ) {
        AcademiesByLocationWithScrollResults academiesByLocationWithScroll =
            academyService.findAcademiesByLocationWithScroll(request.to(memberId));

        return ResponseEntity.status(HttpStatus.OK)
                .body(AcademiesByLocationWithScrollResponses.from(academiesByLocationWithScroll));
    }

    @GetMapping(
        path = "/complexes-cursor",
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AcademyByLocationWithCursorResponses> findByLocationWithCursor(
        @ModelAttribute @Valid AcademyByLocationWithCursorRequest request,
        @MemberId Long memberId
    ) {
        AcademyByLocationWithCursorResults academiesByLocationWithCursor
            = academyService.findAcademiesByLocationWithCursor(request.to(memberId));

        return ResponseEntity.status(HttpStatus.OK)
            .body(AcademyByLocationWithCursorResponses.from(academiesByLocationWithCursor));
    }

    @GetMapping(
            path = "/search",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AcademiesByNameResponses> findByName(
            @ModelAttribute @Valid AcademiesByNameRequest request
    ) {
        AcademiesByNameResults academiesByNameResults = academyService.findAcademiesByName(
                AcademiesByNameRequest.to(request));

        return ResponseEntity.status(HttpStatus.OK)
                .body(AcademiesByNameResponses.from(academiesByNameResults));
    }

    @GetMapping(
            path = "/filter-scroll",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AcademiesFilterWithScrollResponses> filterAcademies(
            @ModelAttribute @Valid AcademyFilterWithScrollRequest request,
            @MemberId Long memberId
    ) {
        AcademiesFilterWithScrollResults academiesFilterWithScrollResults = academyService.filterAcademies(
                AcademyFilterWithScrollRequest.to(request), memberId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(AcademiesFilterWithScrollResponses.from(academiesFilterWithScrollResults));
    }

    @GetMapping(
            path = "/{academyId}/lessons",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<LessonInfoToCreateDashboardResponses> getLessonInfoToCreateDashboard(
            @PathVariable Long academyId) {
        LessonInfoToCreateDashboardResults lessonsInfoAboutAcademy = academyService.getLessonsInfoAboutAcademy(
                academyId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(LessonInfoToCreateDashboardResponses.from(lessonsInfoAboutAcademy));
    }

}
