package org.guzzing.studayserver.domain.calendar.service.dto.param;

public record AcademyCalendarDeleteParam(
        Long academyScheduleId,
        boolean isAllDeleted
) {

}
