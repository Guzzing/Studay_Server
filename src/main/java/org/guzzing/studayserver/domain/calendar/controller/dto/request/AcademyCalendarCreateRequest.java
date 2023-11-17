package org.guzzing.studayserver.domain.calendar.controller.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.guzzing.studayserver.domain.calendar.controller.dto.request.validation.ValidAttendanceDate;
import org.guzzing.studayserver.domain.calendar.controller.dto.request.validation.ValidDateString;
import org.guzzing.studayserver.domain.calendar.controller.dto.request.validation.ValidEnum;
import org.guzzing.studayserver.domain.calendar.model.Periodicity;
import org.guzzing.studayserver.domain.calendar.service.dto.param.AcademyCalendarCreateParam;

import java.time.LocalDate;
import java.util.List;

public record AcademyCalendarCreateRequest(
        @Valid
        @Size(min = 1, message = "최소한 하나 이상의 레슨 스케줄이 필요합니다.")
        List<LessonScheduleCreateRequest> lessonScheduleParams,

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

    public static AcademyCalendarCreateParam to(AcademyCalendarCreateRequest request, Long memberId) {
        return new AcademyCalendarCreateParam(
                request.lessonScheduleParams
                        .stream()
                        .map(lesson -> LessonScheduleCreateRequest.to(lesson))
                        .toList(),
                LocalDate.parse(request.attendanceDate().getStartDateOfAttendance()),
                LocalDate.parse(request.attendanceDate().getEndDateOfAttendance()),
                request.isAlarmed,
                memberId,
                request.childId,
                request.dashboardId,
                request.memo,
                Periodicity.valueOf(request.periodicity)
        );
    }

}
