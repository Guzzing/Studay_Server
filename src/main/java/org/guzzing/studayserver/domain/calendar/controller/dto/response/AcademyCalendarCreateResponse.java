package org.guzzing.studayserver.domain.calendar.controller.dto.response;

import org.guzzing.studayserver.domain.calendar.service.dto.result.AcademyCalendarCreateResults;

import java.util.List;

public record AcademyCalendarCreateResponse(
        List<Long> academyTimeTemplateIds
) {
    public static AcademyCalendarCreateResponse from(AcademyCalendarCreateResults results) {
        return new AcademyCalendarCreateResponse(
                results.academyTimeTemplateIds()
        );
    }
}
