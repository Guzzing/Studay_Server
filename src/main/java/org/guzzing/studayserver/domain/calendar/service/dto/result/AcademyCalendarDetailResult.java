package org.guzzing.studayserver.domain.calendar.service.dto.result;

import java.time.LocalDate;
import java.time.LocalTime;
import org.guzzing.studayserver.domain.calendar.repository.dto.AcademyCalenderDetailInfo;
public record AcademyCalendarDetailResult(
        Long dashboardId,
        LocalTime lessonStartTime,
        LocalTime lessonEndTime,
        String memo,
        LocalDate requestedDate
) {

    public static AcademyCalendarDetailResult from(
            AcademyCalenderDetailInfo academyCalenderDetailInfo,
            LocalDate requestedDate) {
        return new AcademyCalendarDetailResult(
                academyCalenderDetailInfo.getDashboardId(),
                academyCalenderDetailInfo.getLessonStartTime(),
                academyCalenderDetailInfo.getLessonEndTime(),
                academyCalenderDetailInfo.getMemo(),
                requestedDate
        );
    }
}


