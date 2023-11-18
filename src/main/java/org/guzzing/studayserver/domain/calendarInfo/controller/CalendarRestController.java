package org.guzzing.studayserver.domain.calendarInfo.controller;

import jakarta.validation.Valid;
import java.util.ArrayList;
import org.guzzing.studayserver.domain.auth.memberId.MemberId;
import org.guzzing.studayserver.domain.calendarInfo.controller.request.CalendarMonthMarkRequest;
import org.guzzing.studayserver.domain.calendarInfo.controller.response.CalendarMonthMarkResponse;
import org.guzzing.studayserver.domain.calendarInfo.controller.response.CalendarMonthMarkResponse.DateRange;
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

    @GetMapping(
            path = "/mark",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CalendarMonthMarkResponse> getMonthMark(
            @MemberId Long memberId,
            @RequestParam @Valid CalendarMonthMarkRequest calendarMonthMarkRequest
    ) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new CalendarMonthMarkResponse(
                        new DateRange(1, 31),
                        new ArrayList<>(),
                        new ArrayList<>()
                ));
    }
}
