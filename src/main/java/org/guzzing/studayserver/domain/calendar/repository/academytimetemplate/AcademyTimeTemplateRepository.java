package org.guzzing.studayserver.domain.calendar.repository.academytimetemplate;

import java.time.DayOfWeek;
import java.util.List;
import org.guzzing.studayserver.domain.calendar.model.AcademyTimeTemplate;
import org.guzzing.studayserver.domain.calendar.repository.dto.AcademyTimeTemplateDateInfo;

public interface AcademyTimeTemplateRepository {

    AcademyTimeTemplate save(AcademyTimeTemplate academyTimeTemplate);

    List<AcademyTimeTemplateDateInfo> findAcademyTimeTemplateByDashboardId(Long dashboardId, DayOfWeek dayOfWeek);
    List<AcademyTimeTemplateDateInfo> findAcademyTimeTemplateByDashboardId(Long dashboardId);

    void deleteById(Long academyTimeTemplateId);

    AcademyTimeTemplate getById(Long academyTimeTemplateId);

    List<Long> findByChildIdIn(List<Long> childIds);

    void deleteAllByChildIds(List<Long> childIds);
}
