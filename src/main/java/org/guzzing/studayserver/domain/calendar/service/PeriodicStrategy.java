package org.guzzing.studayserver.domain.calendar.service;

import static org.guzzing.studayserver.global.error.response.ErrorCode.NOT_GENERATED_SCHEDULED;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.guzzing.studayserver.domain.calendar.exception.NotGeneratedScheduleException;
import org.guzzing.studayserver.domain.calendar.model.Periodicity;
import org.guzzing.studayserver.domain.calendar.service.dto.RepeatPeriod;
import org.guzzing.studayserver.domain.calendar.service.scheduler.AcademyScheduleMaker;
import org.guzzing.studayserver.domain.calendar.service.scheduler.ScheduleDailyMaker;
import org.guzzing.studayserver.domain.calendar.service.scheduler.ScheduleMonthMaker;
import org.guzzing.studayserver.domain.calendar.service.scheduler.ScheduleWeekMaker;
import org.guzzing.studayserver.domain.calendar.service.scheduler.ScheduleYearlyMaker;
import org.springframework.stereotype.Component;

@Component
public class PeriodicStrategy {

    private Map<Periodicity, AcademyScheduleMaker> periodicStrategies = new HashMap<>();

    private PeriodicStrategy(Map<Periodicity, AcademyScheduleMaker> periodicStrategies) {
        this.periodicStrategies = periodicStrategies;
    }

    public PeriodicStrategy() {
        periodicStrategies.put(Periodicity.DAILY, new ScheduleDailyMaker());
        periodicStrategies.put(Periodicity.MONTHLY, new ScheduleMonthMaker());
        periodicStrategies.put(Periodicity.WEEKLY, new ScheduleWeekMaker());
        periodicStrategies.put(Periodicity.YEARLY, new ScheduleYearlyMaker());
    }

    public List<LocalDate> createSchedules(RepeatPeriod repeatPeriod) {
        List<LocalDate> generatedSchedules = periodicStrategies.get(repeatPeriod.periodicity())
                .generateSchedules(repeatPeriod);

        if (generatedSchedules.size() == 0 || generatedSchedules == null) {
            throw new NotGeneratedScheduleException(NOT_GENERATED_SCHEDULED);
        }

        return generatedSchedules;
    }

}
