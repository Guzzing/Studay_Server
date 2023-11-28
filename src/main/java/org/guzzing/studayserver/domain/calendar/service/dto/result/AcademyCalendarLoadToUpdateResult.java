package org.guzzing.studayserver.domain.calendar.service.dto.result;

import java.time.LocalDate;
import java.util.List;
import org.guzzing.studayserver.domain.calendar.model.AcademyTimeTemplate;
import org.guzzing.studayserver.domain.dashboard.service.access.dto.DashboardScheduleAccessResult;
import org.guzzing.studayserver.domain.dashboard.service.access.dto.LessonScheduleAccessResult;

public record AcademyCalendarLoadToUpdateResult(
        String childName,
        String academyName,
        String lessonName,
        List<LessonScheduleAccessResult> lessonScheduleAccessResults,
        LocalDate startDateOfAttendance,
        LocalDate endDateOfAttendance,
        boolean isAlarmed,
        String memo
) {

    public static AcademyCalendarLoadToUpdateResult of(
            AcademyTimeTemplate academyTimeTemplate,
            DashboardScheduleAccessResult dashboardScheduleAccessResult
    ) {
        return new AcademyCalendarLoadToUpdateResult(
                dashboardScheduleAccessResult.childName(),
                dashboardScheduleAccessResult.academyName(),
                dashboardScheduleAccessResult.lessonName(),
                dashboardScheduleAccessResult.lessonScheduleInAccessResponses(),
                academyTimeTemplate.getStartDateOfAttendance(),
                academyTimeTemplate.getEndDateOfAttendance(),
                academyTimeTemplate.isAlarmed(),
                academyTimeTemplate.getMemo()
        );

    }
}
