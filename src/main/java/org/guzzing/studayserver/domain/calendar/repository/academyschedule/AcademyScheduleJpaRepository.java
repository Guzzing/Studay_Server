package org.guzzing.studayserver.domain.calendar.repository.academyschedule;

import org.guzzing.studayserver.domain.calendar.model.AcademySchedule;
import org.guzzing.studayserver.domain.calendar.model.AcademyTimeTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

public interface AcademyScheduleJpaRepository extends JpaRepository<AcademySchedule, Long>, AcademyScheduleRepository {

    AcademySchedule save(AcademySchedule academySchedule);

    @Query("SELECT distinct ash.academyTimeTemplate FROM AcademySchedule as ash WHERE ash.id = :academyScheduleId ")
    AcademyTimeTemplate findDistinctAcademyTimeTemplate(@Param(value = "academyScheduleId") Long academyScheduleId);

    void deleteAllByAcademyTimeTemplateId(Long academyTimeTemplateId);

    @Modifying
    @Transactional
    @Query("""
            DELETE FROM AcademySchedule AS ash 
            WHERE ash.academyTimeTemplate.id = :academyTimeTemplateId 
            AND ash.scheduleDate >= :startDateOfAttendanceToUpdate
            """)
    void deleteAfterUpdatedStartDate(@Param(value = "academyTimeTemplateId") Long academyTimeTemplateId,
                                     @Param(value = "startDateOfAttendanceToUpdate") LocalDate startDateOfAttendanceToUpdate);

    List<AcademySchedule> findByAcademyTimeTemplateId(Long academyTimeTemplateId);

    void deleteAcademyScheduleById(Long academyScheduleId);



}
