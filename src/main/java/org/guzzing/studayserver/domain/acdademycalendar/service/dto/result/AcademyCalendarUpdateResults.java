package org.guzzing.studayserver.domain.acdademycalendar.service.dto.result;

import java.util.List;

public record AcademyCalendarUpdateResults(
        List<Long> academyTimeTemplateIds
) {
    public static AcademyCalendarUpdateResults to(AcademyCalendarCreateResults academyCalendarCreateResults) {
        return new AcademyCalendarUpdateResults(
                academyCalendarCreateResults.academyTimeTemplateIds()
        );
    }
}
