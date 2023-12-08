package org.guzzing.studayserver.domain.calendar.service;

import java.util.List;
import java.util.stream.Collectors;
import org.guzzing.studayserver.domain.calendar.exception.DateOverlapException;
import org.guzzing.studayserver.domain.calendar.model.AcademySchedule;
import org.guzzing.studayserver.domain.calendar.service.dto.GeneratedLessonSchedule;
import org.guzzing.studayserver.global.error.response.ErrorCode;

public class DateTimeOverlapChecker {

    private DateTimeOverlapChecker() {
        throw new RuntimeException(ErrorCode.UTIL_NOT_CONSTRUCTOR.getMessage());
    }

    public static void checkOverlap(List<AcademySchedule> existedDateOverlappingSchedules,
            List<GeneratedLessonSchedule> generatedSchedules) throws DateOverlapException {

        List<AcademySchedule> overlappingSchedules = existedDateOverlappingSchedules.stream()
                .filter(existedSchedule -> generatedSchedules.stream()
                        .anyMatch(generatedSchedule -> checkOverlap(existedSchedule, generatedSchedule)))
                .toList();

        if (!overlappingSchedules.isEmpty()) {
            throw new DateOverlapException(ErrorCode.DATE_TIME_OVERLAP_ERROR,
                    overlappingSchedules
                            .stream()
                            .map(AcademySchedule::getDashboardId)
                            .collect(Collectors.toSet()));
        }
    }

    private static boolean checkOverlap(AcademySchedule existedSchedule, GeneratedLessonSchedule generatedSchedule) {
        return existedSchedule.getScheduleDate().equals(generatedSchedule.scheduleDate()) &&
                !(generatedSchedule.lessonStartTime().isAfter(existedSchedule.getLessonEndTime()) ||
                        generatedSchedule.lessonEndTime().isBefore(existedSchedule.getLessonStartTime()));
    }

}
