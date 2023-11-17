package org.guzzing.studayserver.domain.calendar.service.dto;

import org.guzzing.studayserver.domain.calendar.model.Periodicity;

import java.time.DayOfWeek;
import java.time.LocalDate;

public record RepeatPeriod(
        LocalDate startDateOfAttendance,
        LocalDate endDateOfAttendance,
        DayOfWeek dayOfWeek,
        Periodicity periodicity
) {

    public static RepeatPeriod of(
            LocalDate startDateOfAttendance,
            LocalDate endDateOfAttendance,
            DayOfWeek dayOfWeek,
            Periodicity periodicity
    ) {
        return new RepeatPeriod(
                startDateOfAttendance,
                endDateOfAttendance,
                dayOfWeek,
                periodicity
        );
    }

}
