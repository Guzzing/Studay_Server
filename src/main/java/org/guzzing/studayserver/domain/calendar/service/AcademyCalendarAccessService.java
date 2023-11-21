package org.guzzing.studayserver.domain.calendar.service;

import java.util.List;
import org.guzzing.studayserver.domain.calendar.service.dto.param.AcademyCalendarDeleteParam;

public interface AcademyCalendarAccessService {

    void deleteSchedule(AcademyCalendarDeleteParam academyCalendarDeleteParam);
}
