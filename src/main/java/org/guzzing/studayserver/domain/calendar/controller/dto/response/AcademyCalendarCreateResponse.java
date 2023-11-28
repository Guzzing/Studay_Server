package org.guzzing.studayserver.domain.calendar.controller.dto.response;

import java.util.List;

import org.guzzing.studayserver.domain.calendar.service.dto.result.AcademyCalendarCreateResults;

public record AcademyCalendarCreateResponse(
        List<Long> academyTimeTemplateIds
) {

    public static AcademyCalendarCreateResponse from(AcademyCalendarCreateResults results) {
        return new AcademyCalendarCreateResponse(
                results.academyTimeTemplateIds()
        );
    }
}
