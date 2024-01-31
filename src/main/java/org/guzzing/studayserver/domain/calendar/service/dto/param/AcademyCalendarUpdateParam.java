package org.guzzing.studayserver.domain.calendar.service.dto.param;

import java.time.LocalDate;
import java.util.List;

public record AcademyCalendarUpdateParam(
        LessonScheduleParam lessonScheduleParams,
        LocalDate startDateOfAttendance,
        LocalDate endDateOfAttendance,
        boolean isAlarmed,
        Long memberId,
        Long childId,
        Long dashboardId,
        String memo,
        boolean isAllUpdated
) {

}
