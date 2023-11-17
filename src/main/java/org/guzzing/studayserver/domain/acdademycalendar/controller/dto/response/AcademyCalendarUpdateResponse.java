package org.guzzing.studayserver.domain.acdademycalendar.controller.dto.response;

import org.guzzing.studayserver.domain.acdademycalendar.service.dto.result.AcademyCalendarUpdateResults;

import java.util.List;

public record AcademyCalendarUpdateResponse (
        List<Long> academyCalendarIds
) {
    public static AcademyCalendarUpdateResponse from(AcademyCalendarUpdateResults results) {
        return new AcademyCalendarUpdateResponse(
                results.academyTimeTemplateIds()
        );
    }
}
