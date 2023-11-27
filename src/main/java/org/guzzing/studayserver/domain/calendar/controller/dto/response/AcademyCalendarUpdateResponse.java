package org.guzzing.studayserver.domain.calendar.controller.dto.response;

import java.util.List;

import org.guzzing.studayserver.domain.calendar.service.dto.result.AcademyCalendarUpdateResults;

public record AcademyCalendarUpdateResponse(
        List<Long> academyCalendarIds
) {

    public static AcademyCalendarUpdateResponse from(AcademyCalendarUpdateResults results) {
        return new AcademyCalendarUpdateResponse(
                results.academyTimeTemplateIds()
        );
    }
}
