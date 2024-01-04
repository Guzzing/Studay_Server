package org.guzzing.studayserver.domain.calendar.controller;

import jakarta.validation.Valid;
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
import org.guzzing.studayserver.domain.calendar.facade.dto.AcademyScheduleLoadToUpdateFacadeResult;
import org.guzzing.studayserver.domain.calendar.service.AcademyCalendarService;
import org.guzzing.studayserver.domain.calendar.service.dto.result.AcademyCalendarCreateResults;
import org.guzzing.studayserver.domain.calendar.service.dto.result.AcademyCalendarUpdateResults;
import org.guzzing.studayserver.global.common.member.MemberId;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/academy-schedules")
public class AcademyCalendarController {

    private final AcademyCalendarService academyCalendarService;
    private final AcademyCalendarFacade academyCalendarFacade;

    public AcademyCalendarController(AcademyCalendarService academyCalendarService,
            AcademyCalendarFacade academyCalendarFacade) {
        this.academyCalendarService = academyCalendarService;
        this.academyCalendarFacade = academyCalendarFacade;
    }

    @PostMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AcademyCalendarCreateResponse> createAcademyCalendar(
            @RequestBody @Valid AcademyCalendarCreateRequest request
    ) {

        AcademyCalendarCreateResults schedules = academyCalendarService.createSchedules(
                AcademyCalendarCreateRequest.to(request)
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(AcademyCalendarCreateResponse.from(schedules));
    }

    @GetMapping(
            path = "/{academyScheduleId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AcademyCalendarLoadToUpdateResponse> loadTimeTemplateToUpdate(
            @PathVariable Long academyScheduleId) {

        AcademyScheduleLoadToUpdateFacadeResult academyScheduleLoadToUpdateFacadeResult
                = academyCalendarFacade.loadTimeTemplateToUpdate(academyScheduleId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(AcademyCalendarLoadToUpdateResponse.from(academyScheduleLoadToUpdateFacadeResult));
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
            path = "/{scheduleId}"
    )
    public ResponseEntity<Void> deleteSchedule(
            @PathVariable Long scheduleId,
            @RequestParam boolean isAllDeleted
    ) {
        academyCalendarService.deleteSchedule(AcademyCalendarDeleteRequest.to(scheduleId, isAllDeleted));

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @GetMapping(
            path = "/detail/{scheduleId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<AcademyCalendarDetailResponse> getDetailSchedule(
            @PathVariable Long scheduleId,
            @ModelAttribute AcademyCalendarDetailRequest academyCalendarDetailRequest
    ) {
        AcademyCalendarDetailFacadeResult calendarDetailInfo
                = academyCalendarFacade.getCalendarDetailInfo(
                AcademyCalendarDetailRequest.to(scheduleId, academyCalendarDetailRequest));

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(AcademyCalendarDetailResponse.from(calendarDetailInfo));
    }

}
