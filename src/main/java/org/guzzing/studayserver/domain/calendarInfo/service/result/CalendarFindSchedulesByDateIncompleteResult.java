package org.guzzing.studayserver.domain.calendarInfo.service.result;

import java.time.LocalTime;
import org.guzzing.studayserver.domain.calendar.service.dto.result.AcademyScheduleFindByDateResults.AcademyScheduleFindByDateResult;
import org.guzzing.studayserver.domain.dashboard.service.dto.response.DashBoardFindByIdsResults.DashBoardFindByIdsResult;

public record CalendarFindSchedulesByDateIncompleteResult(
        Long childId,
        Long academyScheduleId,
        LocalTime startTime,
        LocalTime endTime,
        Long lessonId
) {

    public static CalendarFindSchedulesByDateIncompleteResult of(
            AcademyScheduleFindByDateResult schedule, DashBoardFindByIdsResult dashboard) {
        return new CalendarFindSchedulesByDateIncompleteResult(
                schedule.childId(),
                schedule.academyScheduleId(),
                schedule.startTime(),
                schedule.endTime(),
                dashboard.lessonId()
        );
    }
}
