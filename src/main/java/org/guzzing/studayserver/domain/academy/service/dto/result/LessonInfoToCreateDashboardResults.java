package org.guzzing.studayserver.domain.academy.service.dto.result;

import org.guzzing.studayserver.domain.academy.repository.dto.LessonInfoToCreateDashboard;

import java.util.List;

public record LessonInfoToCreateDashboardResults(
        List<LessonInfoToCreateDashboardResult> lessonInfoToCreateDashboardResults
) {
    public static LessonInfoToCreateDashboardResults from(List<LessonInfoToCreateDashboard> lessonInfoToCreateDashboards) {
        return new LessonInfoToCreateDashboardResults(
                lessonInfoToCreateDashboards.stream()
                        .map(lessonInfoToCreateDashboard -> LessonInfoToCreateDashboardResult.from(lessonInfoToCreateDashboard))
                        .toList()
        );
    }

    public record LessonInfoToCreateDashboardResult(
            Long lessonId,
            String subject
    ) {
        public static LessonInfoToCreateDashboardResult from(LessonInfoToCreateDashboard lessonInfoToCreateDashboard) {
            return new LessonInfoToCreateDashboardResult(
                    lessonInfoToCreateDashboard.getId(),
                    lessonInfoToCreateDashboard.getSubject()
            );
        }
    }
}
