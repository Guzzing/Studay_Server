package org.guzzing.studayserver.domain.child.service;

import java.time.LocalDate;
import java.time.LocalTime;

public record ChildDateScheduleResult(
        Long childId,
        LocalDate schedule_date,
        LocalTime lessonStartTime,
        LocalTime lessonEndTime,
        String academyName,
        String lessonSubject
) {

}
