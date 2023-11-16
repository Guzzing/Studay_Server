package org.guzzing.studayserver.domain.acdademycalendar.service.scheduler;

import java.time.LocalDate;
public class ScheduleDailyMaker implements AcademyScheduleMaker {

    private static final Long DAILY = 1L;

    @Override
    public LocalDate getNextRepeatedDate(LocalDate baseDate) {
        return baseDate.plusDays(DAILY);
    }

}
