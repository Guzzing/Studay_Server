package org.guzzing.studayserver.domain.calendarInfo.controller;

import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import org.guzzing.studayserver.domain.auth.memberId.MemberId;
import org.guzzing.studayserver.domain.calendar.service.dto.result.CalendarFindSchedulesByDateResults;
import org.guzzing.studayserver.domain.calendarInfo.controller.request.CalendarYearMonthMarkRequest;
import org.guzzing.studayserver.domain.calendarInfo.controller.response.CalendarFindSchedulesByDateResponses;
import org.guzzing.studayserver.domain.calendarInfo.controller.response.CalendarYearMonthMarkResponse;
import org.guzzing.studayserver.domain.calendarInfo.service.CalendarFacade;
import org.guzzing.studayserver.domain.calendarInfo.service.result.CalendarYearMonthMarkResult;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/calendar")
public class CalendarRestController {

    private final CalendarFacade calendarFacade;

    public CalendarRestController(CalendarFacade calendarFacade) {
        this.calendarFacade = calendarFacade;
    }

    @GetMapping(
            path = "/mark",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CalendarYearMonthMarkResponse> findYearMonthMark(
            @MemberId Long memberId,
            @RequestParam @Valid CalendarYearMonthMarkRequest request
    ) {

        CalendarYearMonthMarkResult result = calendarFacade.findYearMonthMark(request.toParam(memberId));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(CalendarYearMonthMarkResponse.from(result));
    }

    @GetMapping(
            path = "/date",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CalendarFindSchedulesByDateResponses> findSchedulesByDate(
            @MemberId Long memberId,
            @RequestParam @DateTimeFormat(iso = ISO.DATE) LocalDate date
    ) {
        CalendarFindSchedulesByDateResults results = calendarFacade.findSchedulesByDate(memberId, date);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(CalendarFindSchedulesByDateResponses.from(
                        date,
                        results
                ));
    }
}
