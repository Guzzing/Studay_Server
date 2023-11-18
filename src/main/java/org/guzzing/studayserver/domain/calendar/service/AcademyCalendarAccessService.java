package org.guzzing.studayserver.domain.calendar.service;

import org.guzzing.studayserver.domain.calendar.service.dto.param.AcademyCalendarDeleteParam;

public interface AcademyCalendarAccessService {

    void deleteSchedule(AcademyCalendarDeleteParam academyCalendarDeleteParam);

}
