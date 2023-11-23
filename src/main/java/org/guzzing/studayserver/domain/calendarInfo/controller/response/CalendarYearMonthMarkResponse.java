package org.guzzing.studayserver.domain.calendarInfo.controller.response;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import org.guzzing.studayserver.domain.calendarInfo.service.result.CalendarYearMonthMarkResult;
import org.guzzing.studayserver.domain.calendarInfo.service.result.CalendarYearMonthMarkResult.HolidayResult;

public record CalendarYearMonthMarkResponse(
        int lastDay,
        List<HolidayResponse> holidays,
        List<Integer> existenceDays
) {

    public static CalendarYearMonthMarkResponse from(CalendarYearMonthMarkResult result) {
        List<HolidayResponse> holidayResponses = result.holidayResults().stream()
                .sorted(Comparator.comparing(HolidayResult::date))
                .map(r -> new HolidayResponse(r.date(), r.names()))
                .toList();

        List<Integer> existenceDays = result.existenceDays().stream()
                .sorted()
                .map(LocalDate::getDayOfMonth)
                .toList();

        return new CalendarYearMonthMarkResponse(
                result.lastDayOfMonth(),
                holidayResponses,
                existenceDays
        );
    }

    public record HolidayResponse(
            LocalDate date,
            List<String> names
    ) {

        public HolidayResponse {
            validateNames(names);
        }

        private void validateNames(List<String> names) {
            if (names.isEmpty()) {
                throw new IllegalStateException("공휴일의 이름이 없습니다.");
            }
        }
    }
}
