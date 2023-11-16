package org.guzzing.studayserver.domain.acdademycalendar.service.scheduler;

import java.time.LocalDate;

public class ScheduleMonthMaker implements AcademyScheduleMaker {

    private static final Long MONTHLY = 1L;

    @Override
    public LocalDate getNextRepeatedDate(LocalDate baseDate) {
        return baseDate.plusMonths(MONTHLY);
    }

}
