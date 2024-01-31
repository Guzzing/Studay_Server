package org.guzzing.studayserver.domain.calendar.service.dto.result;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.guzzing.studayserver.domain.calendar.model.AcademyTimeTemplate;
import org.guzzing.studayserver.domain.calendar.repository.dto.AcademyScheduleLoadInfo;
import org.guzzing.studayserver.domain.dashboard.service.dto.response.DashboardScheduleAccessResult;
import org.guzzing.studayserver.domain.dashboard.service.access.dto.LessonScheduleAccessResult;

public record AcademyCalendarLoadToUpdateResult(
    Long dashboardId,
    LocalDate startDateOfAttendance,
    LocalDate endDateOfAttendance,
    boolean isAlarmed,
    String memo,
    DayOfWeek dayOfWeek,
    LocalTime lessonStartTime,
    LocalTime lessonEndTime
) {
    public static AcademyCalendarLoadToUpdateResult of(
        AcademyScheduleLoadInfo academyTimeTemplate
    ) {
        return new AcademyCalendarLoadToUpdateResult(
            academyTimeTemplate.getDashboardId(),
            academyTimeTemplate.getStartDateOfAttendance(),
            academyTimeTemplate.getEndDateOfAttendance(),
            academyTimeTemplate.getIsAlarmed(),
            academyTimeTemplate.getMemo(),
            academyTimeTemplate.getDayOfWeek(),
            academyTimeTemplate.getLessonStartTime(),
            academyTimeTemplate.getLessonEndTime()
        );
    }

}
