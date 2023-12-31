package org.guzzing.studayserver.domain.calendar.service.dto.result;

import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import org.guzzing.studayserver.domain.academy.service.dto.result.LessonFindByIdsResults.LessonFindByIdsResult;
import org.guzzing.studayserver.domain.calendar_info.service.result.CalendarFindSchedulesByDateIncompleteResult;

public record CalendarFindSchedulesByDateResults(
        List<CalendarFindSchedulesByDateResult> results
) {

    public CalendarFindSchedulesByDateResults(List<CalendarFindSchedulesByDateResult> results) {
        this.results = results.stream()
                .sorted(Comparator.comparing(CalendarFindSchedulesByDateResult::startTime)
                        .thenComparing(CalendarFindSchedulesByDateResult::endTime))
                .toList();
    }

    public record CalendarFindSchedulesByDateResult(
            Long childId,
            String childImageUrl,
            Long academyScheduleId,
            LocalTime startTime,
            LocalTime endTime,
            Long lessonId,
            String academyName,
            String lessonName
    ) {

        public static CalendarFindSchedulesByDateResult of(
                CalendarFindSchedulesByDateIncompleteResult incompleteResult, LessonFindByIdsResult lesson) {
            return new CalendarFindSchedulesByDateResult(
                    incompleteResult.childId(),
                    incompleteResult.childImageUrl(),
                    incompleteResult.academyScheduleId(),
                    incompleteResult.startTime(),
                    incompleteResult.endTime(),
                    lesson.lessonId(),
                    lesson.academyName(),
                    lesson.lessonName()
            );
        }
    }

}
