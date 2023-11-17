package org.guzzing.studayserver.domain.acdademycalendar.controller.dto.request;

import jakarta.validation.constraints.AssertTrue;
import org.guzzing.studayserver.domain.acdademycalendar.model.Periodicity;
import org.guzzing.studayserver.domain.acdademycalendar.service.dto.param.AcademyCalendarCreateParam;
import org.guzzing.studayserver.domain.acdademycalendar.service.dto.param.AcademyCalendarUpdateParam;
import org.guzzing.studayserver.domain.acdademycalendar.service.dto.param.LessonScheduleParam;

import java.time.LocalDate;
import java.util.List;

public record AcademyCalendarUpdateRequest(
        List<LessonScheduleUpdateRequest> lessonScheduleUpdateRequests,
        LocalDate startDateOfAttendance,
        LocalDate endDateOfAttendance,
        boolean isAlarmed,
        Long childId,
        Long dashboardId,
        String memo,
        Periodicity periodicity,
        boolean isAllUpdated
) {
    private static final int MAX_DIFFERENCE_YEAR = 3;

    @AssertTrue(message = "등원 시작일과 등원 마지막일의 차이가 " + MAX_DIFFERENCE_YEAR + "년을 넘을 수 없습니다.")
    private boolean isValidPeriod() {
        return !endDateOfAttendance.isAfter(startDateOfAttendance.plusYears(MAX_DIFFERENCE_YEAR));
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
                request.periodicity,
                request.isAllUpdated
        );
    }
}
