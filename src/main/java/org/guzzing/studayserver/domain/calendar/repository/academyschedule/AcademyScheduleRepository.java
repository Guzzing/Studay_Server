package org.guzzing.studayserver.domain.calendar.repository.academyschedule;

import java.time.LocalDate;
import java.util.List;
import org.guzzing.studayserver.domain.calendar.model.AcademySchedule;
import org.guzzing.studayserver.domain.calendar.model.AcademyTimeTemplate;

public interface AcademyScheduleRepository {

    AcademySchedule save(AcademySchedule academySchedule);

    List<AcademySchedule> findAll();

    AcademyTimeTemplate findDistinctAcademyTimeTemplate(Long academyScheduleId);

    void deleteAllByAcademyTimeTemplateId(Long academyTimeTemplateId);

    void deleteAfterUpdatedStartDate(Long academyTimeTemplateId, LocalDate startDateOfAttendanceToUpdate);

    List<AcademySchedule> findByAcademyTimeTemplateId(Long academyTimeTemplateId);

    void deleteAcademyScheduleById(Long academyScheduleId);

    List<AcademySchedule> findByYearMonth(List<Long> childIds, int year, int month);

    List<AcademySchedule> findByDate(List<Long> childIds, LocalDate date);
}
