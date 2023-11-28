package org.guzzing.studayserver.domain.calendar.repository.academytimetemplate;

import java.util.List;

import org.guzzing.studayserver.domain.calendar.model.AcademyTimeTemplate;
import org.guzzing.studayserver.domain.calendar.repository.dto.AcademyTimeTemplateDateInfo;

public interface AcademyTimeTemplateRepository {

    AcademyTimeTemplate save(AcademyTimeTemplate academyTimeTemplate);

    List<AcademyTimeTemplateDateInfo> findAcademyTimeTemplateByDashboardId(Long dashboardId);

    void deleteById(Long academyTimeTemplateId);

    AcademyTimeTemplate getById(Long academyTimeTemplateId);

}
