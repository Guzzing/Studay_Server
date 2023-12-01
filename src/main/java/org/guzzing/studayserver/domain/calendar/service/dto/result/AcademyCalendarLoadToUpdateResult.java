package org.guzzing.studayserver.domain.calendar.service.dto.result;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.guzzing.studayserver.domain.calendar.model.AcademyTimeTemplate;
import org.guzzing.studayserver.domain.dashboard.service.access.dto.DashboardScheduleAccessResult;
import org.guzzing.studayserver.domain.dashboard.service.access.dto.LessonScheduleAccessResult;

public record AcademyCalendarLoadToUpdateResult(
        Long childId,
        Long academyId,
        Long lessonId,
        Long dashboardId,
        List<LessonScheduleInfo> lessonScheduleAccessResults,
        LocalDate startDateOfAttendance,
        LocalDate endDateOfAttendance,
        boolean isAlarmed,
        String memo
) {

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public static AcademyCalendarLoadToUpdateResult of(
            AcademyTimeTemplate academyTimeTemplate,
            DashboardScheduleAccessResult dashboardScheduleAccessResult
    ) {
        return new AcademyCalendarLoadToUpdateResult(
                dashboardScheduleAccessResult.childId(),
                dashboardScheduleAccessResult.academyId(),
                dashboardScheduleAccessResult.lessonId(),
                academyTimeTemplate.getDashboardId(),
                dashboardScheduleAccessResult.lessonScheduleInAccessResponses()
                        .stream()
                        .map(lessonScheduleAccessResult -> LessonScheduleInfo.from(lessonScheduleAccessResult))
                        .toList(),
                academyTimeTemplate.getStartDateOfAttendance(),
                academyTimeTemplate.getEndDateOfAttendance(),
                academyTimeTemplate.isAlarmed(),
                academyTimeTemplate.getMemo()
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
