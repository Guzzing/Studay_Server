package org.guzzing.studayserver.domain.academy.controller.dto.response;

import java.util.List;
import org.guzzing.studayserver.domain.academy.service.dto.result.LessonInfoToCreateDashboardResults;

public record LessonInfoToCreateDashboardResponses(
        List<LessonInfoToCreateDashboardResponse> lessonInfos
) {

    public static LessonInfoToCreateDashboardResponses from(LessonInfoToCreateDashboardResults results) {
        return new LessonInfoToCreateDashboardResponses(
                results.lessonInfoToCreateDashboardResults()
                        .stream()
                        .map(lessonInfoToCreateDashboardResult ->
                                LessonInfoToCreateDashboardResponse.from(lessonInfoToCreateDashboardResult))
                        .toList()
        );
    }

    public record LessonInfoToCreateDashboardResponse(
            Long lessonId,
            String subject
    ) {

        public static LessonInfoToCreateDashboardResponse from(
                LessonInfoToCreateDashboardResults.
                        LessonInfoToCreateDashboardResult result) {
            return new LessonInfoToCreateDashboardResponse(
                    result.lessonId(),
                    result.subject()
            );
        }
    }
}
