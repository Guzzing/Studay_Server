package org.guzzing.studayserver.domain.calendar.service.dto.result;

import org.guzzing.studayserver.domain.calendar.model.AcademyTimeTemplate;

import java.util.List;

public record AcademyCalendarCreateResults(
        List<Long> academyTimeTemplateIds
) {

    public static AcademyCalendarCreateResults of(List<AcademyTimeTemplate> academyTimeTemplates) {
        return new AcademyCalendarCreateResults(
                academyTimeTemplates
                        .stream()
                        .map(academyTimeTemplate -> academyTimeTemplate.getId())
                        .toList()
        );
    }

}
