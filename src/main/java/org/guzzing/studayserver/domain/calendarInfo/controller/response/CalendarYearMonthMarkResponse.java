package org.guzzing.studayserver.domain.calendarInfo.controller.response;

import java.time.LocalDate;
import java.util.List;

public record CalendarYearMonthMarkResponse(
        DateRange dateRange,
        List<HolidayResponse> holidayResponses,
        List<Integer> existenceDays
) {

    public record DateRange(
            int firstDay,
            int lastDay
    ) {

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
