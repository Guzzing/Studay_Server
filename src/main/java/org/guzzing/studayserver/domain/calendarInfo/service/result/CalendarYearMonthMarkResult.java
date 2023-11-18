package org.guzzing.studayserver.domain.calendarInfo.service.result;

public record CalendarYearMonthMarkResult(
        int lastDayOfMonth
) {

    public static CalendarYearMonthMarkResult of(int lastDayOfMonth) {
        return new CalendarYearMonthMarkResult(lastDayOfMonth);
    }
}
