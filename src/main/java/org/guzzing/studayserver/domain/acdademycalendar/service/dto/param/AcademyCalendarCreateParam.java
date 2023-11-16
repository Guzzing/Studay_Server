package org.guzzing.studayserver.domain.acdademycalendar.service.dto.param;

import org.guzzing.studayserver.domain.acdademycalendar.model.AcademyTimeTemplate;
import org.guzzing.studayserver.domain.acdademycalendar.model.Periodicity;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

public record AcademyCalendarCreateParam(
        List<LessonScheduleParam> lessonScheduleParams,
        LocalDate startDateOfAttendance,
        LocalDate endDateOfAttendance,
        boolean isAlarmed,
        Long memberId,
        Long childId,
        Long dashboardId,
        boolean isAllDay,
        String memo,
        Periodicity periodicity
){
    public static AcademyTimeTemplate to(AcademyCalendarCreateParam param, DayOfWeek dayOfWeek) {
        return AcademyTimeTemplate.of(
                dayOfWeek,
                param.startDateOfAttendance,
                param.endDateOfAttendance,
                param.isAlarmed,
                param.memberId,
                param.childId,
                param.dashboardId,
                param.memo
        );
    }

}
