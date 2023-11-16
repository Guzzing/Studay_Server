package org.guzzing.studayserver.domain.acdademycalendar.repository.academytimetemplate;

import org.guzzing.studayserver.domain.acdademycalendar.model.AcademyTimeTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AcademyTimeTemplateJpaRepository extends JpaRepository<AcademyTimeTemplate, Long>, AcademyTimeTemplateRepository {

    AcademyTimeTemplate save(AcademyTimeTemplate academyTimeTemplate);

}
