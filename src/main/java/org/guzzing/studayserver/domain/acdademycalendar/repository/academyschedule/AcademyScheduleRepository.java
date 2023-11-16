package org.guzzing.studayserver.domain.acdademycalendar.repository.academyschedule;

import org.guzzing.studayserver.domain.acdademycalendar.model.AcademySchedule;
import org.guzzing.studayserver.domain.acdademycalendar.model.AcademyTimeTemplate;

import java.util.List;

public interface AcademyScheduleRepository {

    AcademySchedule save(AcademySchedule academySchedule);

    List<AcademySchedule> findAll();

    AcademyTimeTemplate findAcademyTimeTemplateById(Long academyScheduleId);

}
