package org.guzzing.studayserver.domain.academy.service.dto.result;

import java.util.List;
import org.guzzing.studayserver.domain.academy.repository.dto.LessonInfoToCreateDashboard;

public record LessonInfoToCreateDashboardResults(
        List<LessonInfoToCreateDashboardResult> lessonInfoToCreateDashboardResults
) {

    public static LessonInfoToCreateDashboardResults from(
            List<LessonInfoToCreateDashboard> lessonInfoToCreateDashboards) {
        return new LessonInfoToCreateDashboardResults(
                lessonInfoToCreateDashboards.stream()
                        .map(LessonInfoToCreateDashboardResult::from)
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
                    lessonInfoToCreateDashboard.getCurriculum()
            );
        }
    }
}
