package org.guzzing.studayserver.domain.calendar_info.service.util;

import java.time.YearMonth;

public class DateUtility {

    private DateUtility() {
    }

    public static int getLastDayOfMonth(int year, int month) {
        YearMonth yearMonth = YearMonth.of(year, month);
        return yearMonth.lengthOfMonth();
    }
}
