package org.guzzing.studayserver.domain.calendarInfo.controller;

import jakarta.validation.Valid;
import java.util.ArrayList;
import org.guzzing.studayserver.domain.auth.memberId.MemberId;
import org.guzzing.studayserver.domain.calendarInfo.controller.request.CalendarYearMonthMarkRequest;
import org.guzzing.studayserver.domain.calendarInfo.controller.response.CalendarYearMonthMarkResponse;
import org.guzzing.studayserver.domain.calendarInfo.controller.response.CalendarYearMonthMarkResponse.DateRange;
import org.guzzing.studayserver.domain.calendarInfo.service.CalendarFacade;
import org.guzzing.studayserver.domain.calendarInfo.service.result.CalendarYearMonthMarkResult;
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

    private CalendarFacade calendarFacade;

    public CalendarRestController(CalendarFacade calendarFacade) {
        this.calendarFacade = calendarFacade;
    }

    @GetMapping(
            path = "/mark",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CalendarYearMonthMarkResponse> getYearMonthMark(
            @MemberId Long memberId,
            @RequestParam @Valid CalendarYearMonthMarkRequest request
    ) {

        CalendarYearMonthMarkResult result = calendarFacade.getYearMonthMark(request.toParam(memberId));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new CalendarYearMonthMarkResponse(
                        new DateRange(1, 31),
                        new ArrayList<>(),
                        new ArrayList<>()
                ));
    }
}
