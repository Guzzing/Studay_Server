package org.guzzing.studayserver.domain.acdademycalendar.controller.dto.request;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.guzzing.studayserver.domain.acdademycalendar.model.Periodicity;
import org.guzzing.studayserver.domain.acdademycalendar.service.dto.param.AcademyCalendarCreateParam;
import org.guzzing.studayserver.global.validation.EnumValue;

import java.time.LocalDate;
import java.util.List;

public record AcademyCalendarCreateRequest(
        List<LessonScheduleRequest> lessonScheduleParams,
        LocalDate startDateOfAttendance,
        LocalDate endDateOfAttendance,

        @NotBlank
        boolean isAlarmed,

        @EnumValue(enumClass = Periodicity.class, message = "올바른 반복 여부 값이 아닙니다.")
        Periodicity periodicity,

        @NotNull
        Long childId,

        @NotNull
        Long dashboardId,

        @NotNull
        boolean isAllDay,

        @NotBlank
        String memo
) {

    private static final int MAX_DIFFERENCE_YEAR = 3;

    @AssertTrue(message = "등원 시작일과 등원 마지막일의 차이가 " + MAX_DIFFERENCE_YEAR + "년을 넘을 수 없습니다.")
    private boolean isValidPeriod() {
        return !endDateOfAttendance.isAfter(startDateOfAttendance.plusYears(MAX_DIFFERENCE_YEAR));
    }

    public static AcademyCalendarCreateParam to(AcademyCalendarCreateRequest request, Long memberId) {
        return new AcademyCalendarCreateParam(
                request.lessonScheduleParams
                        .stream()
                        .map(lesson -> LessonScheduleRequest.to(lesson))
                        .toList(),
                request.startDateOfAttendance,
                request.endDateOfAttendance,
                request.isAlarmed,
                memberId,
                request.childId,
                request.dashboardId,
                request.isAllDay,
                request.memo,
                request.periodicity

        );
    }

}
