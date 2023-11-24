package org.guzzing.studayserver.domain.calendar.repository.academyschedule;

import java.time.LocalDate;
import java.util.List;
import org.guzzing.studayserver.domain.calendar.model.AcademySchedule;
import org.guzzing.studayserver.domain.calendar.model.AcademyTimeTemplate;
import org.guzzing.studayserver.domain.calendar.repository.dto.AcademyCalenderDetailInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.transaction.annotation.Transactional;

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

    @Query("SELECT ash FROM AcademySchedule ash "
            + "JOIN FETCH ash.academyTimeTemplate att "
            + "WHERE att.childId IN :childIds "
            + "AND YEAR(ash.scheduleDate) = :year "
            + "AND MONTH(ash.scheduleDate) = :month")
    List<AcademySchedule> findByYearMonth(
            @Param("childIds") List<Long> childIds,
            @Param("year") int year,
            @Param("month") int month);

    @Query("""
                select distinct new org.guzzing.studayserver.domain.calendar.repository.dto.AcademyCalenderDetailInfo (
                    a.dashboardId ,
                    a.childId ,
                    ash.lessonStartTime ,
                    ash.lessonEndTime ,
                    a.memo )
                from AcademySchedule ash
                join ash.academyTimeTemplate a
                where ash.id = :scheduleId
                and a.childId = :childId
            """)
    AcademyCalenderDetailInfo findTimeTemplateByChildIdAndScheduleId(
            @Param(value = "scheduleId") Long scheduleId,
            @Param(value = "childId") Long childId
    );


    @Query("SELECT ash FROM AcademySchedule ash "
            + "JOIN FETCH ash.academyTimeTemplate att "
            + "WHERE att.childId IN :childIds "
            + "AND ash.scheduleDate = :date")
    List<AcademySchedule> findByDate(List<Long> childIds, LocalDate date);

}
