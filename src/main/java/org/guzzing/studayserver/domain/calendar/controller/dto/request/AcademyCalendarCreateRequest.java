package org.guzzing.studayserver.domain.calendar.controller.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.guzzing.studayserver.domain.calendar.controller.dto.request.validation.ValidEnum;
import org.guzzing.studayserver.domain.calendar.model.Periodicity;
import org.guzzing.studayserver.domain.calendar.service.dto.param.AcademyCalendarCreateParam;
import org.guzzing.studayserver.domain.calendar.service.dto.param.LessonScheduleParam;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record AcademyCalendarCreateRequest(
        @Valid
        @Size(min = 1, message = "최소한 하나 이상의 레슨 스케줄이 필요합니다.")
        List<LessonScheduleCreateRequest> lessonScheduleCreateRequests,

        @Valid
        AttendanceDate attendanceDate,

        @NotNull
        Boolean isAlarmed,

        @NotBlank
        @ValidEnum(enumClass = Periodicity.class)
        String periodicity,

        @Positive
        @NotNull
        Long childId,

        @Positive
        @NotNull
        Long dashboardId,
        String memo
) {

    public static AcademyCalendarCreateParam to(AcademyCalendarCreateRequest request) {
        return new AcademyCalendarCreateParam(
                request.lessonScheduleCreateRequests
                        .stream()
                        .map(lesson -> LessonScheduleCreateRequest.to(lesson))
                        .toList(),
                LocalDate.parse(request.attendanceDate().getStartDateOfAttendance()),
                LocalDate.parse(request.attendanceDate().getEndDateOfAttendance()),
                request.isAlarmed,
                request.childId,
                request.dashboardId,
                request.memo,
                Periodicity.valueOf(request.periodicity)
        );
    }

    public record LessonScheduleCreateRequest(
            @NotBlank
            @ValidEnum(enumClass = DayOfWeek.class, message = "올바른 요일 표기가 아닙니다.")
            String dayOfWeek,

            @Valid
            LessonTime lessonTime
    ) {
        public static LessonScheduleParam to(LessonScheduleCreateRequest request) {
            return new LessonScheduleParam(
                    DayOfWeek.valueOf(request.dayOfWeek()),
                    LocalTime.parse(request.lessonTime().getLessonStartTime()),
                    LocalTime.parse(request.lessonTime().getLessonEndTime())
            );
        }
    }

}
