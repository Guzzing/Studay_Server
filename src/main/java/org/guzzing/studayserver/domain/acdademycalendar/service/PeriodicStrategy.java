package org.guzzing.studayserver.domain.acdademycalendar.service;

import org.guzzing.studayserver.domain.acdademycalendar.model.Periodicity;
import org.guzzing.studayserver.domain.acdademycalendar.service.dto.RepeatPeriod;
import org.guzzing.studayserver.domain.acdademycalendar.service.scheduler.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class PeriodicStrategy {

    private Map<Periodicity, AcademyScheduleMaker> periodicStrategies = new HashMap<>();

    private PeriodicStrategy(Map<Periodicity, AcademyScheduleMaker> periodicStrategies) {
        this.periodicStrategies = periodicStrategies;
    }

    public PeriodicStrategy() {
        periodicStrategies.put(Periodicity.DAILY,new ScheduleDailyMaker());
        periodicStrategies.put(Periodicity.MONTHLY,new ScheduleMonthMaker());
        periodicStrategies.put(Periodicity.WEEKLY,new ScheduleWeekMaker());
        periodicStrategies.put(Periodicity.YEARLY,new ScheduleYearlyMaker());
    }

    public List<LocalDate> createSchedules(RepeatPeriod repeatPeriod) {
        return periodicStrategies.get(repeatPeriod.periodicity())
                .generateSchedules(repeatPeriod);
    }

}