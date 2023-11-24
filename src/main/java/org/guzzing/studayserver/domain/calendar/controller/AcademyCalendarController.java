package org.guzzing.studayserver.domain.calendar.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.guzzing.studayserver.domain.auth.memberId.MemberId;
import org.guzzing.studayserver.domain.calendar.controller.dto.request.AcademyCalendarCreateRequest;
import org.guzzing.studayserver.domain.calendar.controller.dto.request.AcademyCalendarDeleteRequest;
import org.guzzing.studayserver.domain.calendar.controller.dto.request.AcademyCalendarDetailRequest;
import org.guzzing.studayserver.domain.calendar.controller.dto.request.AcademyCalendarUpdateRequest;
import org.guzzing.studayserver.domain.calendar.controller.dto.response.AcademyCalendarCreateResponse;
import org.guzzing.studayserver.domain.calendar.controller.dto.response.AcademyCalendarDetailResponse;
import org.guzzing.studayserver.domain.calendar.controller.dto.response.AcademyCalendarLoadToUpdateResponse;
import org.guzzing.studayserver.domain.calendar.controller.dto.response.AcademyCalendarUpdateResponse;
import org.guzzing.studayserver.domain.calendar.facade.AcademyCalendarFacade;
import org.guzzing.studayserver.domain.calendar.facade.dto.AcademyCalendarDetailFacadeResult;
import org.guzzing.studayserver.domain.calendar.service.AcademyCalendarService;
import org.guzzing.studayserver.domain.calendar.service.dto.result.AcademyCalendarCreateResults;
import org.guzzing.studayserver.domain.calendar.service.dto.result.AcademyCalendarLoadToUpdateResult;
import org.guzzing.studayserver.domain.calendar.service.dto.result.AcademyCalendarUpdateResults;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/academy-schedules")
public class AcademyCalendarController {

    private final AcademyCalendarService academyCalendarService;
    private final AcademyCalendarFacade academyCalendarFacade;

    public AcademyCalendarController(AcademyCalendarService academyCalendarService, AcademyCalendarFacade academyCalendarFacade) {
        this.academyCalendarService = academyCalendarService;
        this.academyCalendarFacade = academyCalendarFacade;
    }

    @PostMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AcademyCalendarCreateResponse> createAcademyCalendar(
            @RequestBody @Valid AcademyCalendarCreateRequest request,
            @MemberId Long memberId
    ) {

        AcademyCalendarCreateResults schedules = academyCalendarService.createSchedules(
                AcademyCalendarCreateRequest.to(request, memberId)
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(AcademyCalendarCreateResponse.from(schedules));
    }

    @GetMapping(
            path = "/{academyScheduleId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AcademyCalendarLoadToUpdateResponse> loadTimeTemplateToUpdate(
            @PathVariable @NotNull Long academyScheduleId) {

        AcademyCalendarLoadToUpdateResult academyCalendarLoadToUpdateResult = academyCalendarService.loadTimeTemplateToUpdate(
                academyScheduleId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(AcademyCalendarLoadToUpdateResponse.from(academyCalendarLoadToUpdateResult));
    }

    @PutMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<AcademyCalendarUpdateResponse> updateSchedule(
            @RequestBody @Valid AcademyCalendarUpdateRequest request,
            @MemberId Long memberId
    ) {
        AcademyCalendarUpdateResults academyCalendarUpdateResults =
                academyCalendarService.updateTimeTemplate(AcademyCalendarUpdateRequest.to(request, memberId));

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(AcademyCalendarUpdateResponse.from(academyCalendarUpdateResults));
    }

    @DeleteMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Void> deleteSchedule(
            @RequestBody @Valid AcademyCalendarDeleteRequest academyCalendarDeleteRequest
    ) {
        academyCalendarService.deleteSchedule(AcademyCalendarDeleteRequest.to(academyCalendarDeleteRequest));

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @GetMapping(
            path = "/detail",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<AcademyCalendarDetailResponse> getDetailSchedule(
            @ModelAttribute AcademyCalendarDetailRequest academyCalendarDetailRequest
    ) {
        System.out.println("Received request: " + academyCalendarDetailRequest);
        AcademyCalendarDetailFacadeResult calendarDetailInfo
                = academyCalendarFacade.getCalendarDetailInfo(AcademyCalendarDetailRequest.to(academyCalendarDetailRequest));

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(AcademyCalendarDetailResponse.from(calendarDetailInfo));
    }

}
