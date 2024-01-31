package org.guzzing.studayserver.domain.calendar.repository.academytimetemplate;

import java.time.DayOfWeek;
import java.util.List;
import org.guzzing.studayserver.domain.calendar.model.AcademyTimeTemplate;
import org.guzzing.studayserver.domain.calendar.repository.dto.AcademyTimeTemplateDateInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.transaction.annotation.Transactional;

public interface AcademyTimeTemplateJpaRepository extends JpaRepository<AcademyTimeTemplate, Long>,
        AcademyTimeTemplateRepository {

    AcademyTimeTemplate save(AcademyTimeTemplate academyTimeTemplate);

    @Query(
        """
            select att
            from AcademyTimeTemplate as att
            where att.dashboardId =:dashboardId
            and att.dayOfWeek =:dayOfWeek
            """
    )
    List<AcademyTimeTemplateDateInfo> findAcademyTimeTemplateByDashboardId(
        @Param("dashboardId") Long dashboardId,
        @Param("dayOfWeek") DayOfWeek dayOfWeek);


    List<AcademyTimeTemplateDateInfo> findAcademyTimeTemplateByDashboardId(Long dashboardId);

    void deleteById(Long academyTimeTemplateId);

    AcademyTimeTemplate getById(Long academyTimeTemplateId);

    @Query("select att.id from AcademyTimeTemplate att where att.childId in :childIds")
    List<Long> findByChildIdIn(List<Long> childIds);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("delete from AcademyTimeTemplate att where att.childId in :childIds")
    void deleteAllByChildIds(List<Long> childIds);

}
