package org.guzzing.studayserver.domain.acdademycalendar.service.scheduler;

import java.time.LocalDate;

public class ScheduleWeekMaker implements AcademyScheduleMaker {

    private static final Long WEEKLY = 1L;

    @Override
    public LocalDate getNextRepeatedDate(LocalDate baseDate) {
        return baseDate.plusWeeks(WEEKLY);
    }
}
