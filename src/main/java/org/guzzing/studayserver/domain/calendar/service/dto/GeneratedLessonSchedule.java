package org.guzzing.studayserver.domain.calendar.service.dto;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

public record GeneratedLessonSchedule(
        LocalDate scheduleDate,
        LocalTime lessonStartTime,
        LocalTime lessonEndTime,
        DayOfWeek dayOfWeek
) {
    public static GeneratedLessonSchedule of(
            LocalDate scheduleDate,
            LocalTime lessonStartTime,
            LocalTime lessonEndTime,
            DayOfWeek dayOfWeek
    ) {
        return new GeneratedLessonSchedule(
                scheduleDate,
                lessonStartTime,
                lessonEndTime,
                dayOfWeek
        );
    }

}
