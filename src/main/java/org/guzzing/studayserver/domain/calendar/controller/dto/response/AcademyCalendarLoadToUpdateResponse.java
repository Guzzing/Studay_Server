package org.guzzing.studayserver.domain.calendar.controller.dto.response;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import org.guzzing.studayserver.domain.calendar.service.dto.result.AcademyCalendarLoadToUpdateResult;

public record AcademyCalendarLoadToUpdateResponse(
        Long childId,
        Long academyId,
        Long lessonId,
        List<LessonScheduleLoadToUpdateResponse> lessonSchedule,
        LocalDate startDateOfAttendance,
        LocalDate endDateOfAttendance,
        boolean isAlarmed,
        String memo
) {

    public static AcademyCalendarLoadToUpdateResponse from(AcademyCalendarLoadToUpdateResult result) {
        return new AcademyCalendarLoadToUpdateResponse(
                result.childId(),
                result.academyId(),
                result.lessonId(),
                result.lessonScheduleAccessResults().stream()
                        .map(lessonScheduleAccessResult ->
                                LessonScheduleLoadToUpdateResponse.from(lessonScheduleAccessResult))
                        .toList(),
                result.startDateOfAttendance(),
                result.endDateOfAttendance(),
                result.isAlarmed(),
                result.memo()
        );
    }

    public record LessonScheduleLoadToUpdateResponse(
            DayOfWeek dayOfWeek,
            String lessonStartTime,
            String lessonEndTime
    ) {

        public static LessonScheduleLoadToUpdateResponse from(
                AcademyCalendarLoadToUpdateResult.LessonScheduleInfo result) {
            return new LessonScheduleLoadToUpdateResponse(
                    result.dayOfWeek(),
                    result.lessonStartTime(),
                    result.lessonEndTime()
            );
        }
    }
}
