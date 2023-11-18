package org.guzzing.studayserver.domain.calendarInfo.controller.response;

import java.time.LocalDate;
import java.util.List;

public record CalendarYearMonthMarkResponse(
        DateRange dateRange,
        List<Holiday> holidays,
        List<Integer> existenceDays
) {

    public record DateRange(
            int firstDay,
            int lastDay
    ) {

    }

    public record Holiday(
            LocalDate date,
            List<String> names
    ) {

        public Holiday {
            validateNames(names);
        }

        private void validateNames(List<String> names) {
            if (names.isEmpty()) {
                throw new IllegalStateException("공휴일의 이름이 없습니다.");
            }
        }
    }
}
