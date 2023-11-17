package org.guzzing.studayserver.domain.calendar.controller.dto.response;

import org.guzzing.studayserver.domain.dashboard.LessonScheduleAccessResult;

import java.time.DayOfWeek;
import java.time.LocalTime;

public record LessonScheduleLoadToUpdateResponse(
        DayOfWeek dayOfWeek,
        LocalTime lessonStartTime,
        LocalTime lessonEndTime
) {
    public static LessonScheduleLoadToUpdateResponse from(LessonScheduleAccessResult result) {
        return new LessonScheduleLoadToUpdateResponse(
                result.dayOfWeek(),
                result.lessonStartTime(),
                result.lessonEndTime()
        );
    }
}
