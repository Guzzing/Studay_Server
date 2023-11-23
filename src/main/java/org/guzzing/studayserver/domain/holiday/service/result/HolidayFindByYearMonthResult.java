package org.guzzing.studayserver.domain.holiday.service.result;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public record HolidayFindByYearMonthResult(
        List<HolidayResult> holidayResults
) {

    public static HolidayFindByYearMonthResult from(Map<LocalDate, List<String>> holidayMap) {
        List<HolidayResult> holidayResults = holidayMap.entrySet().stream()
                .map(entry -> new HolidayResult(entry.getKey(), entry.getValue()))
                .toList();

        return new HolidayFindByYearMonthResult(holidayResults);
    }

    public record HolidayResult(
            LocalDate date,
            List<String> names
    ) {

    }

}
