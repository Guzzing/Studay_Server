package org.guzzing.studayserver.domain.dashboard.service.access.dto;

import java.time.DayOfWeek;
import java.time.LocalTime;

public record LessonScheduleAccessResult(
        DayOfWeek dayOfWeek,
        LocalTime lessonStartTime,
        LocalTime lessonEndTime
) {
}
