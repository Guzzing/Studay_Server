package org.guzzing.studayserver.domain.calendar.controller.dto.response;

import java.time.DayOfWeek;
import java.time.LocalTime;
import org.guzzing.studayserver.domain.dashboard.service.access.dto.LessonScheduleAccessResult;

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
