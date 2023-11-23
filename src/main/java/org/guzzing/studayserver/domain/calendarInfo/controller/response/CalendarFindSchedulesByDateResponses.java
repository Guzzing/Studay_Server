package org.guzzing.studayserver.domain.calendarInfo.controller.response;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.guzzing.studayserver.domain.calendar.service.dto.result.CalendarFindSchedulesByDateResults;
import org.guzzing.studayserver.domain.calendar.service.dto.result.CalendarFindSchedulesByDateResults.CalendarFindSchedulesByDateResult;

public record CalendarFindSchedulesByDateResponses(
        LocalDate date,
        List<CalendarFindSchedulesByDateResponse> dateResponses
) {

    public static CalendarFindSchedulesByDateResponses from(
            LocalDate date, CalendarFindSchedulesByDateResults results) {
        Map<LocalTime, List<CalendarFindSchedulesByDateResult>> schedulesGroupedByStartTime =
                results.results().stream()
                        .collect(Collectors.groupingBy(CalendarFindSchedulesByDateResult::startTime));

        List<CalendarFindSchedulesByDateResponse> dateResponses = new ArrayList<>();
        for (LocalTime startTime : schedulesGroupedByStartTime.keySet()) {
            Map<String, List<CalendarFindSchedulesByDateResult>> schedulesGroupedByAcademyAndEndTime =
                    schedulesGroupedByStartTime.get(startTime).stream()
                            .collect(Collectors.groupingBy(
                                    result -> result.lessonId() + "_"
                                            + result.academyName() + "_"
                                            + result.lessonName() + "_"
                                            + result.endTime()));

            List<SameStartTimeScheduleResponse> sameStartTimeSchedules = new ArrayList<>();
            for (Map.Entry<String, List<CalendarFindSchedulesByDateResult>> entry : schedulesGroupedByAcademyAndEndTime.entrySet()) {
                String key = entry.getKey();
                String[] parts = key.split("_");
                Long lessonId = Long.parseLong(parts[0]);
                String academyName = parts[1];
                String lessonName = parts[2];
                LocalTime endTime = LocalTime.parse(parts[3]);

                List<OverlappingScheduleResponse> overlappingResponses = new ArrayList<>();
                for (CalendarFindSchedulesByDateResult result : entry.getValue()) {
                    overlappingResponses.add(new OverlappingScheduleResponse(
                            result.academyScheduleId(),
                            true
                    ));
                }

                sameStartTimeSchedules.add(new SameStartTimeScheduleResponse(
                        lessonId,
                        academyName,
                        lessonName,
                        endTime,
                        overlappingResponses
                ));
            }
            dateResponses.add(new CalendarFindSchedulesByDateResponse(startTime, sameStartTimeSchedules));
        }

        dateResponses.sort(Comparator.comparing(r -> r.startTime));
        return new CalendarFindSchedulesByDateResponses(
                date,
                dateResponses
        );
    }

    public record CalendarFindSchedulesByDateResponse(
            LocalTime startTime,
            List<SameStartTimeScheduleResponse> schedules
    ) {

    }

    public record SameStartTimeScheduleResponse(
            Long lessonId,
            String academyName,
            String lessonName,
            LocalTime endTIme,
            List<OverlappingScheduleResponse> overlappingSchedules
    ) {

    }

    public record OverlappingScheduleResponse(
            long scheduleId,
            boolean isRepeatable
    ) {

    }
}
