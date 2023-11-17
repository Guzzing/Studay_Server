package org.guzzing.studayserver.domain.acdademycalendar.controller.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.guzzing.studayserver.domain.acdademycalendar.controller.dto.request.validation.ValidEnum;
import org.guzzing.studayserver.domain.acdademycalendar.model.Periodicity;
import org.guzzing.studayserver.domain.acdademycalendar.service.dto.param.AcademyCalendarCreateParam;

import java.time.LocalDate;
import java.util.List;

public record AcademyCalendarCreateRequest(
        @Valid
        @Size(min = 1, message = "최소한 하나 이상의 레슨 스케줄이 필요합니다.")
        List<LessonScheduleCreateRequest> lessonScheduleParams,

        @NotNull
        LocalDate startDateOfAttendance,

        @NotNull
        LocalDate endDateOfAttendance,

        @NotNull
        Boolean isAlarmed,

        @NotBlank
        @ValidEnum(enumClass = Periodicity.class)
        String periodicity,

        @NotNull
        Long childId,

        @NotNull
        Long dashboardId,
        String memo
) {

    private static final int MAX_DIFFERENCE_YEAR = 3;

    @AssertTrue(message = "등원 시작일과 등원 마지막일의 차이가 " + MAX_DIFFERENCE_YEAR + "년을 넘을 수 없습니다.")
    private  boolean isExceedMaxDifferenceYear() {
        return !endDateOfAttendance.isAfter(startDateOfAttendance.plusYears(MAX_DIFFERENCE_YEAR));
    }

    @AssertTrue(message = "마지막 등원일이 등원 시작일보다 이전일 수 없습니다.")
    private  boolean isBeforeStartDate() {
        return !endDateOfAttendance.isBefore(startDateOfAttendance);
    }

    public static AcademyCalendarCreateParam to(AcademyCalendarCreateRequest request, Long memberId) {
        return new AcademyCalendarCreateParam(
                request.lessonScheduleParams
                        .stream()
                        .map(lesson -> LessonScheduleCreateRequest.to(lesson))
                        .toList(),
                request.startDateOfAttendance,
                request.endDateOfAttendance,
                request.isAlarmed,
                memberId,
                request.childId,
                request.dashboardId,
                request.memo,
                Periodicity.valueOf(request.periodicity)
        );
    }

}
