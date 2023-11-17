package org.guzzing.studayserver.domain.acdademycalendar.controller.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.guzzing.studayserver.domain.acdademycalendar.controller.dto.request.validation.ValidEnum;
import org.guzzing.studayserver.domain.acdademycalendar.model.Periodicity;
import org.guzzing.studayserver.domain.acdademycalendar.service.dto.param.AcademyCalendarUpdateParam;

import java.time.LocalDate;
import java.util.List;

public record AcademyCalendarUpdateRequest(
        @Valid
        @Size(min = 1, message = "최소한 하나 이상의 레슨 스케줄이 필요합니다.")
        List<LessonScheduleUpdateRequest> lessonScheduleUpdateRequests,

        @NotNull
        LocalDate startDateOfAttendance,

        @NotNull
        LocalDate endDateOfAttendance,

        @NotNull
        Boolean isAlarmed,

        @NotNull
        Long childId,

        @NotNull
        Long dashboardId,
        String memo,

        @NotBlank
        @ValidEnum(enumClass = Periodicity.class)
        String periodicity,

        @NotNull
        Boolean isAllUpdated
) {
    private static final int MAX_DIFFERENCE_YEAR = 3;

    @AssertTrue(message = "등원 시작일과 등원 마지막일의 차이가 " + MAX_DIFFERENCE_YEAR + "년을 넘을 수 없습니다.")
    private  boolean isExceedMaxDifferenceYear(
    ) {
        return !endDateOfAttendance.isAfter(startDateOfAttendance.plusYears(MAX_DIFFERENCE_YEAR));
    }

    @AssertTrue(message = "마지막 등원일이 등원 시작일보다 이전일 수 없습니다.")
    private  boolean isBeforeStartDate(
    ) {
        return !endDateOfAttendance.isBefore(startDateOfAttendance);
    }

    public static AcademyCalendarUpdateParam to(AcademyCalendarUpdateRequest request, Long memberId) {
        return new AcademyCalendarUpdateParam(
                request.lessonScheduleUpdateRequests
                        .stream()
                        .map(lesson -> LessonScheduleUpdateRequest.to(lesson))
                        .toList(),
                request.startDateOfAttendance,
                request.endDateOfAttendance,
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
