package org.guzzing.studayserver.domain.acdademycalendar.service.dto.param;

import org.guzzing.studayserver.domain.acdademycalendar.model.Periodicity;

import java.time.LocalDate;
import java.util.List;

/**
 * 앞에서는 String으로 받을 것을 잊지 말자
 */
public record AcademyCalendarUpdateParam(
        List<LessonScheduleParam> lessonScheduleParams,
        LocalDate startDateOfAttendance,
        LocalDate endDateOfAttendance,
        boolean isAlarmed,
        Long memberId,
        Long childId,
        Long dashboardId,
        String memo,
        Periodicity periodicity,
        boolean isAllUpdated
) {
}
