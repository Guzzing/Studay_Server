package org.guzzing.studayserver.domain.calendar.facade.dto;

import org.guzzing.studayserver.domain.calendar.service.dto.param.AcademyCalendarDetailParam;

public record AcademyCalendarDetailFacadeParam(
        Long lessonId,
        Long childId,
        Long scheduleId
) {

    public static AcademyCalendarDetailParam to(AcademyCalendarDetailFacadeParam param) {
        return new AcademyCalendarDetailParam(
                param.childId(),
                param.scheduleId()
        );
    }

}
