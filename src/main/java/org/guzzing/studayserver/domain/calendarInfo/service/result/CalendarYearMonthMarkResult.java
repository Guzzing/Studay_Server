package org.guzzing.studayserver.domain.calendarInfo.service.result;

import java.time.LocalDate;
import java.util.List;
import org.guzzing.studayserver.domain.holiday.service.result.HolidayFindByYearMonthResult;

public record CalendarYearMonthMarkResult(
        int lastDayOfMonth,
        List<HolidayResult> holidayResults
) {

    public static CalendarYearMonthMarkResult of(int lastDayOfMonth,
            HolidayFindByYearMonthResult holidayFindByYearMonthResult) {
        List<HolidayResult> holidayResults = holidayFindByYearMonthResult.holidayResults().stream()
                .map(r -> new HolidayResult(r.date(), r.names()))
                .toList();

        return new CalendarYearMonthMarkResult(
                lastDayOfMonth,
                holidayResults
        );
    }

    public record HolidayResult(
            LocalDate date,
            List<String> names
    ) {

    }
}
