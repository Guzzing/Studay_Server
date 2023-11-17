package org.guzzing.studayserver.domain.acdademycalendar.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.guzzing.studayserver.domain.acdademycalendar.controller.dto.request.AcademyCalendarCreateRequest;
import org.guzzing.studayserver.domain.acdademycalendar.controller.dto.request.AcademyCalendarUpdateRequest;
import org.guzzing.studayserver.domain.acdademycalendar.controller.dto.response.AcademyCalendarCreateResponse;
import org.guzzing.studayserver.domain.acdademycalendar.controller.dto.response.AcademyCalendarLoadToUpdateResponse;
import org.guzzing.studayserver.domain.acdademycalendar.controller.dto.response.AcademyCalendarUpdateResponse;
import org.guzzing.studayserver.domain.acdademycalendar.service.AcademyCalendarService;
import org.guzzing.studayserver.domain.acdademycalendar.service.dto.result.AcademyCalendarCreateResults;
import org.guzzing.studayserver.domain.acdademycalendar.service.dto.result.AcademyCalendarLoadToUpdateResult;
import org.guzzing.studayserver.domain.acdademycalendar.service.dto.result.AcademyCalendarUpdateResults;
import org.guzzing.studayserver.domain.auth.memberId.MemberId;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/academy-schedules")
public class AcademyCalendarController {

    private final AcademyCalendarService academyCalendarService;

    public AcademyCalendarController(AcademyCalendarService academyCalendarService) {
        this.academyCalendarService = academyCalendarService;
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

        AcademyCalendarLoadToUpdateResult academyCalendarLoadToUpdateResult = academyCalendarService.loadTimeTemplateToUpdate(academyScheduleId);

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

}
