package org.guzzing.studayserver.domain.calendarInfo.service.util;

import java.time.YearMonth;

public class DateUtility {

    public static int getLastDayOfMonth(int year, int month) {
        YearMonth yearMonth = YearMonth.of(year, month);
        return yearMonth.lengthOfMonth();
    }
}
