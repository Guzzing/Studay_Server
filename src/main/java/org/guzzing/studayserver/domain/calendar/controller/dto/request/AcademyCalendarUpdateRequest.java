package org.guzzing.studayserver.domain.calendar.controller.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

import org.guzzing.studayserver.domain.calendar.controller.dto.request.validation.ValidEnum;
import org.guzzing.studayserver.domain.calendar.service.dto.param.AcademyCalendarUpdateParam;
import org.guzzing.studayserver.domain.calendar.service.dto.param.LessonScheduleParam;

public record AcademyCalendarUpdateRequest(

    @Valid
    @NotNull
    LessonScheduleUpdateRequest lessonScheduleUpdateRequests,

    @Valid
    AttendanceDate attendanceDate,

    @NotNull
    Boolean isAlarmed,

    @Positive
    @NotNull
    Long childId,

    @Positive
    @NotNull
    Long dashboardId,
    String memo,

    @NotNull
    Boolean isAllUpdated
) {

    public static AcademyCalendarUpdateParam to(AcademyCalendarUpdateRequest request, Long memberId) {
        return new AcademyCalendarUpdateParam(
            LessonScheduleUpdateRequest.to(request.lessonScheduleUpdateRequests),
            LocalDate.parse(request.attendanceDate().startDateOfAttendance()),
            LocalDate.parse(request.attendanceDate().endDateOfAttendance()),
            request.isAlarmed,
            memberId,
            request.childId,
            request.dashboardId,
            request.memo,
            request.isAllUpdated
        );
    }

    public record LessonScheduleUpdateRequest(
        @NotBlank
        @ValidEnum(enumClass = DayOfWeek.class, message = "올바른 요일 표기가 아닙니다.")
        String dayOfWeek,

        @Valid
        LessonTime lessonTime
    ) {

        public static LessonScheduleParam to(LessonScheduleUpdateRequest request) {
            return new LessonScheduleParam(
                DayOfWeek.valueOf(request.dayOfWeek()),
                LocalTime.parse(request.lessonTime().getLessonStartTime()),
                LocalTime.parse(request.lessonTime().getLessonEndTime())
            );
        }
    }
}
