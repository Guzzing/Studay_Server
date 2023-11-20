package org.guzzing.studayserver.domain.calendar.service.dto.result;

import java.util.List;

public record CalendarFindSchedulesByDateResults(
        List<CalendarFindSchedulesByDateResult> results
) {

    public record CalendarFindSchedulesByDateResult(

    ) {

    }

}
