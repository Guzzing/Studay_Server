package org.guzzing.studayserver.domain.calendar.service;

import org.guzzing.studayserver.domain.calendar.service.dto.param.AcademyCalendarDeleteParam;
import org.springframework.stereotype.Service;

@Service
public class AcademyCalendarAccessServiceImpl {

    private final AcademyCalendarService academyCalendarService;

    public AcademyCalendarAccessServiceImpl(AcademyCalendarService academyCalendarService) {
        this.academyCalendarService = academyCalendarService;
    }

    public void deleteSchedule(AcademyCalendarDeleteParam academyCalendarDeleteParam) {
        academyCalendarService.deleteSchedule(academyCalendarDeleteParam);
    }

}
