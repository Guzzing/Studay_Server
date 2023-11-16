package org.guzzing.studayserver.domain.acdademycalendar.service.dto.param;

import java.time.DayOfWeek;
import java.time.LocalTime;

public record LessonScheduleParam(
        DayOfWeek dayOfWeek,
        LocalTime lessonStartTime,
        LocalTime lessonEndTime
) {

}
