package org.guzzing.studayserver.domain.dashboard;

import org.guzzing.studayserver.domain.acdademycalendar.model.Periodicity;

import java.util.List;

public record DashboardScheduleAccessResult(
        String childName,
        String academyName,
        String lessonName,
        Periodicity periodicity,
        List<LessonScheduleAccessResult> lessonScheduleInAccessResponses
) {
}
