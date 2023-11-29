package org.guzzing.studayserver.domain.dashboard.service.access.dto;

import java.util.List;
import org.guzzing.studayserver.domain.calendar.model.Periodicity;

public record DashboardScheduleAccessResult(
        Long childId,
        Long academyId,
        Long lessonId,
        Periodicity periodicity,
        List<LessonScheduleAccessResult> lessonScheduleInAccessResponses
) {

}
