package org.guzzing.studayserver.domain.calendar.controller.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.guzzing.studayserver.domain.calendar.controller.dto.request.validation.ValidEnum;
import org.guzzing.studayserver.domain.calendar.model.Periodicity;
import org.guzzing.studayserver.domain.calendar.service.dto.param.AcademyCalendarUpdateParam;

import java.time.LocalDate;
import java.util.List;

public record AcademyCalendarUpdateRequest(
        @Valid
        @Size(min = 1, message = "최소한 하나 이상의 레슨 스케줄이 필요합니다.")
        List<LessonScheduleUpdateRequest> lessonScheduleUpdateRequests,

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

        @NotBlank
        @ValidEnum(enumClass = Periodicity.class)
        String periodicity,

        @NotNull
        Boolean isAllUpdated
) {

    public static AcademyCalendarUpdateParam to(AcademyCalendarUpdateRequest request, Long memberId) {
        return new AcademyCalendarUpdateParam(
                request.lessonScheduleUpdateRequests
                        .stream()
                        .map(lesson -> LessonScheduleUpdateRequest.to(lesson))
                        .toList(),
                LocalDate.parse(request.attendanceDate().getStartDateOfAttendance()),
                LocalDate.parse(request.attendanceDate().getEndDateOfAttendance()),
                request.isAlarmed,
                memberId,
                request.childId,
                request.dashboardId,
                request.memo,
                Periodicity.valueOf(request.periodicity),
                request.isAllUpdated
        );
    }
}
