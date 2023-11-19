package org.guzzing.studayserver.domain.calendarInfo.controller.response;

import java.time.LocalDate;
import java.util.List;

public record CalendarFindSchedulesByDateResponses(
        LocalDate date,
        List<CalendarFindSchedulesByDateResponse> dateResponses
) {

    public record CalendarFindSchedulesByDateResponse(
            String startTime,
            List<SameStartTimeScheduleResponse> schedules
    ) {

    }

    public record SameStartTimeScheduleResponse(
            String academyName,
            String endTIme,
            List<OverlappingScheduleResponse> overlappingSchedules
    ) {

    }

    public record OverlappingScheduleResponse(
            long scheduleId,
            boolean isRepeatable
    ) {

    }
}
