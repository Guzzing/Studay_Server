package org.guzzing.studayserver.domain.calendar.controller.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.guzzing.studayserver.domain.calendar.controller.dto.request.validation.ValidEnum;
import org.guzzing.studayserver.domain.calendar.service.dto.param.LessonScheduleParam;

import java.time.DayOfWeek;
import java.time.LocalTime;

public record LessonScheduleCreateRequest(
        @NotBlank
        @ValidEnum(enumClass = DayOfWeek.class, message = "올바른 요일 표기가 아닙니다.")
        String dayOfWeek,

        @NotNull
        LocalTime lessonStartTime,

        @NotNull
        LocalTime lessonEndTime
) {
    public static LessonScheduleParam to(LessonScheduleCreateRequest request) {
        return new LessonScheduleParam(
                DayOfWeek.valueOf(request.dayOfWeek),
                request.lessonStartTime,
                request.lessonEndTime
        );
    }
}
