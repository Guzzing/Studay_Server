package org.guzzing.studayserver.domain.calendar.controller.dto.request;

import org.guzzing.studayserver.domain.calendar.service.dto.param.AcademyCalendarDeleteParam;

public record AcademyCalendarDeleteRequest(
        Long academyScheduleId,
        Boolean isAllDeleted
) {

    public static AcademyCalendarDeleteParam to(Long academyScheduleId, boolean isAllDeleted) {
        return new AcademyCalendarDeleteParam(
                academyScheduleId,
                isAllDeleted
        );
    }

}
