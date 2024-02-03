package org.guzzing.studayserver.domain.calendar.facade.dto;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.guzzing.studayserver.domain.calendar.service.dto.result.AcademyCalendarLoadToUpdateResult;
import org.guzzing.studayserver.domain.dashboard.service.access.dto.LessonScheduleAccessResult;
import org.guzzing.studayserver.domain.dashboard.service.dto.response.DashboardScheduleAccessResult;

public record AcademyScheduleLoadToUpdateFacadeResult(
        Long childId,
        Long academyId,
        Long lessonId,
        Long dashboardId,
        LessonScheduleInfo lessonSchedule,
        LocalDate startDateOfAttendance,
        LocalDate endDateOfAttendance,
        boolean isAlarmed,
        String memo
) {

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public static AcademyScheduleLoadToUpdateFacadeResult from(
            AcademyCalendarLoadToUpdateResult academyCalendarLoadToUpdateResult,
            DashboardScheduleAccessResult dashboardScheduleAccessResult
    ) {
        return new AcademyScheduleLoadToUpdateFacadeResult(
                dashboardScheduleAccessResult.childId(),
                dashboardScheduleAccessResult.academyId(),
                dashboardScheduleAccessResult.lessonId(),
                academyCalendarLoadToUpdateResult.dashboardId(),
                new LessonScheduleInfo(
                    academyCalendarLoadToUpdateResult.dayOfWeek(),
                    academyCalendarLoadToUpdateResult.lessonStartTime().format(TIME_FORMATTER),
                    academyCalendarLoadToUpdateResult.lessonEndTime().format(TIME_FORMATTER)),
                academyCalendarLoadToUpdateResult.startDateOfAttendance(),
                academyCalendarLoadToUpdateResult.endDateOfAttendance(),
                academyCalendarLoadToUpdateResult.isAlarmed(),
                academyCalendarLoadToUpdateResult.memo()
        );
    }

    public record LessonScheduleInfo(
            DayOfWeek dayOfWeek,
            String lessonStartTime,
            String lessonEndTime
    ) {

        public static LessonScheduleInfo from(LessonScheduleAccessResult lessonScheduleAccessResult) {
            return new LessonScheduleInfo(
                    lessonScheduleAccessResult.dayOfWeek(),
                    lessonScheduleAccessResult.lessonStartTime().format(TIME_FORMATTER),
                    lessonScheduleAccessResult.lessonEndTime().format(TIME_FORMATTER)
            );
        }
    }
}
