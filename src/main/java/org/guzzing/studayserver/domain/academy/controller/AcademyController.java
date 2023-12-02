package org.guzzing.studayserver.domain.academy.controller;

import jakarta.validation.Valid;
import org.guzzing.studayserver.domain.academy.controller.dto.request.*;
import org.guzzing.studayserver.domain.academy.controller.dto.response.*;
import org.guzzing.studayserver.domain.academy.facade.AcademyFacade;
import org.guzzing.studayserver.domain.academy.facade.dto.AcademiesByLocationFacadeResult;
import org.guzzing.studayserver.domain.academy.facade.dto.AcademiesByLocationWithScrollFacadeResult;
import org.guzzing.studayserver.domain.academy.service.AcademyService;
import org.guzzing.studayserver.domain.academy.service.dto.result.AcademiesByNameResults;
import org.guzzing.studayserver.domain.academy.service.dto.result.AcademiesFilterWithScrollResults;
import org.guzzing.studayserver.domain.academy.service.dto.result.AcademyFilterResults;
import org.guzzing.studayserver.domain.academy.service.dto.result.LessonInfoToCreateDashboardResults;
import org.guzzing.studayserver.domain.auth.memberId.MemberId;
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
        return ResponseEntity.status(HttpStatus.OK)
                .body(AcademyGetResponse.from(academyService.getAcademy(academyId, memberId)));
    }

    @GetMapping(
            path = "/complexes",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AcademiesByLocationResponses> findByLocation(
            @ModelAttribute @Valid AcademiesByLocationRequest request,
            @MemberId Long memberId
    ) {
        AcademiesByLocationFacadeResult response = academyFacade.findByLocation(
                AcademiesByLocationRequest.to(request, memberId));

        return ResponseEntity.status(HttpStatus.OK)
                .body(AcademiesByLocationResponses.from(response));
    }

    @GetMapping(
            path = "/complexes-scroll",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AcademiesByLocationWithScrollResponses> findByLocationWithScroll(
            @ModelAttribute @Valid AcademyByLocationWithScrollRequest request,
            @MemberId Long memberId
    ) {
        AcademiesByLocationWithScrollFacadeResult response = academyFacade.findByLocationWithScroll(
                AcademyByLocationWithScrollRequest.to(request, memberId));

        return ResponseEntity.status(HttpStatus.OK)
                .body(AcademiesByLocationWithScrollResponses.from(response));
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
            path = "/filter",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AcademyFilterResponses> filterAcademies(
            @ModelAttribute @Valid AcademyFilterRequest request,
            @MemberId Long memberId
    ) {
        AcademyFilterResults academyFilterResults = academyService.filterAcademies(
                AcademyFilterRequest.to(request), memberId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(AcademyFilterResponses.from(academyFilterResults));
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
    public ResponseEntity<LessonInfoToCreateDashboardResponses> getLessonInfosToCreateDashboard(
            @PathVariable Long academyId) {
        LessonInfoToCreateDashboardResults lessonsInfoAboutAcademy = academyService.getLessonsInfoAboutAcademy(
                academyId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(LessonInfoToCreateDashboardResponses.from(lessonsInfoAboutAcademy));
    }

}
