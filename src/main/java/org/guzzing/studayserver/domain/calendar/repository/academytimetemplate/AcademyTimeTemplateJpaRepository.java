package org.guzzing.studayserver.domain.calendar.repository.academytimetemplate;

import java.util.List;
import org.guzzing.studayserver.domain.calendar.model.AcademyTimeTemplate;
import org.guzzing.studayserver.domain.calendar.repository.dto.AcademyTimeTemplateDateInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface AcademyTimeTemplateJpaRepository extends JpaRepository<AcademyTimeTemplate, Long>,
        AcademyTimeTemplateRepository {

    AcademyTimeTemplate save(AcademyTimeTemplate academyTimeTemplate);

    List<AcademyTimeTemplateDateInfo> findAcademyTimeTemplateByDashboardId(Long dashboardId);

    void deleteById(Long academyTimeTemplateId);

    AcademyTimeTemplate getById(Long academyTimeTemplateId);

    @Query("select att.id from AcademyTimeTemplate att where att.childId in :childIds")
    List<Long> findByChildIdIn(List<Long> childIds);

    @Modifying(clearAutomatically = true)
    @Query("delete from AcademyTimeTemplate att where att.childId in :childIds")
    void deleteAllByChildIds(List<Long> childIds);

}
