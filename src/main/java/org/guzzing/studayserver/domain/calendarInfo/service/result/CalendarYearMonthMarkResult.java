package org.guzzing.studayserver.domain.calendarInfo.service.result;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.guzzing.studayserver.domain.calendar.service.dto.result.AcademyScheduleYearMonthResults;
import org.guzzing.studayserver.domain.calendar.service.dto.result.AcademyScheduleYearMonthResults.AcademyScheduleYearMonthResult;
import org.guzzing.studayserver.domain.holiday.service.result.HolidayFindByYearMonthResult;

public record CalendarYearMonthMarkResult(
        int lastDayOfMonth,
        List<HolidayResult> holidayResults,
        List<LocalDate> existenceDays
) {

    public static CalendarYearMonthMarkResult of(int lastDayOfMonth,
            HolidayFindByYearMonthResult holidayFindByYearMonthResult,
            AcademyScheduleYearMonthResults academyScheduleYearMonthResults) {
        List<HolidayResult> holidayResults = holidayFindByYearMonthResult.holidayResults().stream()
                .map(r -> new HolidayResult(r.date(), r.names()))
                .toList();

        Set<LocalDate> existenceDays = academyScheduleYearMonthResults.academyScheduleYearMonthResults().stream()
                .collect(Collectors.groupingBy(AcademyScheduleYearMonthResult::scheduleDate))
                .keySet();

        return new CalendarYearMonthMarkResult(
                lastDayOfMonth,
                holidayResults,
                existenceDays.stream().toList()
        );
    }

    public record HolidayResult(
            LocalDate date,
            List<String> names
    ) {

    }

}
