package org.guzzing.studayserver.domain.acdademycalendar.repository.academytimetemplate;

import org.guzzing.studayserver.domain.acdademycalendar.model.AcademyTimeTemplate;
import org.guzzing.studayserver.domain.acdademycalendar.repository.dto.AcademyTimeTemplateDateInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AcademyTimeTemplateJpaRepository extends JpaRepository<AcademyTimeTemplate, Long>, AcademyTimeTemplateRepository {

    AcademyTimeTemplate save(AcademyTimeTemplate academyTimeTemplate);

    List<AcademyTimeTemplateDateInfo> findAcademyTimeTemplateByDashboardId(Long dashboardId);
}
