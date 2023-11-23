package org.guzzing.studayserver.domain.calendar.service.dto.result;

import java.time.LocalDate;
import java.util.List;

public record AcademyScheduleYearMonthResults(
        List<AcademyScheduleYearMonthResult> academyScheduleYearMonthResults
) {

    public record AcademyScheduleYearMonthResult(
            Long academyTimeTemplateId,
            Long childId,
            Long academyScheduleId,
            LocalDate scheduleDate
    ) {

    }
}
