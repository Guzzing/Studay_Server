package org.guzzing.studayserver.domain.calendar.controller.dto.response;

import java.time.LocalDate;
import java.util.List;

import org.guzzing.studayserver.domain.calendar.service.dto.result.AcademyCalendarLoadToUpdateResult;

public record AcademyCalendarLoadToUpdateResponse(
        String childName,
        String academyName,
        String lessonName,
        List<LessonScheduleLoadToUpdateResponse> lessonScheduleLoadToUpdateResponses,
        LocalDate startDateOfAttendance,
        LocalDate endDateOfAttendance,
        boolean isAlarmed,
        String memo
) {

    public static AcademyCalendarLoadToUpdateResponse from(AcademyCalendarLoadToUpdateResult result) {
        return new AcademyCalendarLoadToUpdateResponse(
                result.childName(),
                result.academyName(),
                result.lessonName(),
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
}
