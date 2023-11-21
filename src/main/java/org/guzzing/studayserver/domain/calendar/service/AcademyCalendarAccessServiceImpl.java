package org.guzzing.studayserver.domain.calendar.service;

import org.guzzing.studayserver.domain.calendar.service.dto.param.AcademyCalendarDeleteByDashboardParam;
import org.springframework.stereotype.Service;

@Service
public class AcademyCalendarAccessServiceImpl implements AcademyCalendarAccessService {

    private final AcademyCalendarService academyCalendarService;

    public AcademyCalendarAccessServiceImpl(AcademyCalendarService academyCalendarService) {
        this.academyCalendarService = academyCalendarService;
    }

    public void deleteSchedule(AcademyCalendarDeleteByDashboardParam param) {
        academyCalendarService.deleteSchedulesByDashboard(param);
    }

}
