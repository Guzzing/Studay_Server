package org.guzzing.studayserver.domain.calendar.service.dto.param;

import java.time.LocalDate;

public record AcademyCalendarDeleteParam(
        Long dashboardId,
        Long academyScheduleId,
        boolean isAllDeleted,
        LocalDate requestDate
) {

}
