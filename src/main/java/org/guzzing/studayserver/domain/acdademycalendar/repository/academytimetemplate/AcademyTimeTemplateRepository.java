package org.guzzing.studayserver.domain.acdademycalendar.repository.academytimetemplate;

import org.guzzing.studayserver.domain.acdademycalendar.model.AcademyTimeTemplate;
import org.guzzing.studayserver.domain.acdademycalendar.repository.dto.AcademyTimeTemplateDateInfo;

import java.util.List;

public interface AcademyTimeTemplateRepository {
    AcademyTimeTemplate save(AcademyTimeTemplate academyTimeTemplate);

    List<AcademyTimeTemplateDateInfo> findAcademyTimeTemplateByDashboardId(Long dashboardId);

    void deleteById(Long academyTimeTemplateId);

    AcademyTimeTemplate getById(Long academyTimeTemplateId);

}
