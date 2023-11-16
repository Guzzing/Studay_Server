package org.guzzing.studayserver.domain.acdademycalendar.controller.dto.request;

import jakarta.validation.constraints.NotNull;
import org.guzzing.studayserver.domain.acdademycalendar.service.dto.param.LessonScheduleParam;
import org.guzzing.studayserver.global.validation.EnumValue;

import java.time.DayOfWeek;
import java.time.LocalTime;

public record LessonScheduleRequest(
        @EnumValue(enumClass = DayOfWeek.class, message = "올바른 요일 표기가 아닙니다." )
        DayOfWeek dayOfWeek,

        @NotNull
        LocalTime lessonStartTime,

        @NotNull
        LocalTime lessonEndTime
) {
    public static LessonScheduleParam to(LessonScheduleRequest request) {
        return new LessonScheduleParam(
                request.dayOfWeek,
                request.lessonStartTime,
                request.lessonEndTime
        );
    }
}
