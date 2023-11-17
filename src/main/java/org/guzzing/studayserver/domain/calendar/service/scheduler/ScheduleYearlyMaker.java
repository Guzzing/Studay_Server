package org.guzzing.studayserver.domain.calendar.service.scheduler;

import java.time.LocalDate;

public class ScheduleYearlyMaker implements AcademyScheduleMaker {

    private static final Long YEARLY = 1L;

    @Override
    public LocalDate getNextRepeatedDate(LocalDate baseDate) {
        return baseDate.plusYears(YEARLY);
    }
}
