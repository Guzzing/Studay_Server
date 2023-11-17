package org.guzzing.studayserver.domain.calendar.service.dto.param;

import org.guzzing.studayserver.domain.calendar.model.AcademyTimeTemplate;
import org.guzzing.studayserver.domain.calendar.model.Periodicity;

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
        String memo,
        Periodicity periodicity
) {
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

    public static AcademyCalendarCreateParam from(AcademyCalendarUpdateParam param) {
        return new AcademyCalendarCreateParam(
                param.lessonScheduleParams(),
                param.startDateOfAttendance(),
                param.endDateOfAttendance(),
                param.isAlarmed(),
                param.memberId(),
                param.childId(),
                param.dashboardId(),
                param.memo(),
                param.periodicity()
        );

    }

}
