package org.guzzing.studayserver.domain.calendar.service;

import org.guzzing.studayserver.domain.calendar.service.dto.param.AcademyCalendarDeleteByDashboardParam;

public interface AcademyCalendarAccessService {

    void deleteSchedule(AcademyCalendarDeleteByDashboardParam param);

}
