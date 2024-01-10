package org.guzzing.studayserver.domain.dashboard.service.dto.response;

import java.util.List;
import org.guzzing.studayserver.domain.calendar.model.Periodicity;
import org.guzzing.studayserver.domain.dashboard.service.access.dto.LessonScheduleAccessResult;

public record DashboardScheduleAccessResult(
        Long childId,
        Long academyId,
        Long lessonId,
        Periodicity periodicity,
        List<LessonScheduleAccessResult> lessonScheduleInAccessResponses
) {

}
