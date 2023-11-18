package org.guzzing.studayserver.domain.calendar.service.dto.param;

import org.guzzing.studayserver.domain.calendar.model.Periodicity;

import java.time.LocalDate;
import java.util.List;

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
