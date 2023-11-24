package org.guzzing.studayserver.domain.calendar.service.dto.param;

import java.time.LocalDate;

public record AcademyCalendarDeleteByDashboardParam(
        Long dashboardId,
        LocalDate requestedDate
) {

}
