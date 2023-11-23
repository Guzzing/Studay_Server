package org.guzzing.studayserver.domain.calendarInfo.service.result;

import java.time.LocalTime;
import org.guzzing.studayserver.domain.dashboard.service.dto.response.DashBoardFindByIdsResults.DashBoardFindByIdsResult;

public record CalendarFindSchedulesByDateIncompleteResult(
        Long childId,
        String childImageUrl,
        Long academyScheduleId,
        LocalTime startTime,
        LocalTime endTime,
        Long lessonId
) {

    public static CalendarFindSchedulesByDateIncompleteResult of(
            ChildAcademyScheduleCombineResult schedule, DashBoardFindByIdsResult dashboard) {
        return new CalendarFindSchedulesByDateIncompleteResult(
                schedule.childId(),
                schedule.childImageUrl(),
                schedule.academyScheduleId(),
                schedule.startTime(),
                schedule.endTime(),
                dashboard.lessonId()
        );
    }
}
