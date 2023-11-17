package org.guzzing.studayserver.domain.acdademycalendar.service.dto.result;

import java.util.List;

public record AcademyCalendarCreateResults(
        List<Long> academyTimeTemplateIds
) {
    public static AcademyCalendarCreateResults of(List<Long> academyTimeTemplateIds) {
        return new AcademyCalendarCreateResults(
                academyTimeTemplateIds
        );
    }
}