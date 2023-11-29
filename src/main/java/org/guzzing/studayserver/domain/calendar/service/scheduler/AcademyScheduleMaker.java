package org.guzzing.studayserver.domain.calendar.service.scheduler;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.guzzing.studayserver.domain.calendar.service.dto.RepeatPeriod;

public interface AcademyScheduleMaker {

    default List<LocalDate> generateSchedules(RepeatPeriod repeatPeriod) {
        LocalDate startDate = repeatPeriod.startDateOfAttendance();
        LocalDate endDate = repeatPeriod.endDateOfAttendance();
        List<LocalDate> createdDates = new ArrayList<>();

        int offsetDays = calculateOffsetDays(startDate, repeatPeriod.dayOfWeek());
        LocalDate currentRepeatedDate = startDate.plusDays(offsetDays);

        while (!currentRepeatedDate.isAfter(endDate)) {
            createdDates.add(currentRepeatedDate);
            currentRepeatedDate = getNextRepeatedDate(currentRepeatedDate);
        }

        return createdDates;
    }

    default int calculateOffsetDays(LocalDate startDate, DayOfWeek targetDayOfWeek) {
        int offsetDays = targetDayOfWeek.getValue() - startDate.getDayOfWeek().getValue();
        return offsetDays >= 0 ? offsetDays : 7 + offsetDays;
    }

    LocalDate getNextRepeatedDate(LocalDate baseDate);
}
