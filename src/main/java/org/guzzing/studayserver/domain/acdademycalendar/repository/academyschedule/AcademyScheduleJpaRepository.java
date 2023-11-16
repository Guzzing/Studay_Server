package org.guzzing.studayserver.domain.acdademycalendar.repository.academyschedule;

import org.guzzing.studayserver.domain.acdademycalendar.model.AcademySchedule;
import org.guzzing.studayserver.domain.acdademycalendar.model.AcademyTimeTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface AcademyScheduleJpaRepository extends JpaRepository<AcademySchedule, Long>, AcademyScheduleRepository {

    AcademySchedule save(AcademySchedule academySchedule);

    @Query("SELECT distinct ash.academyTimeTemplate FROM AcademySchedule as ash WHERE ash.id = :academyScheduleId ")
    AcademyTimeTemplate findAcademyTimeTemplateById(@Param(value = "academyScheduleId") Long academyScheduleId);
}
