package org.guzzing.studayserver.domain.calendar.service.dto.result;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record AcademyScheduleFindByDateResults(
        List<AcademyScheduleFindByDateResult> academySchedules
) {


    public record AcademyScheduleFindByDateResult(
            long academyScheduleId,
            LocalTime startTime,
            LocalTime endTime,
            long childId
    ) {

    }

}
