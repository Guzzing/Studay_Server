package org.guzzing.studayserver.domain.calendar.service.dto.result;

import java.util.List;
import org.guzzing.studayserver.domain.calendar.model.AcademyTimeTemplate;

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
