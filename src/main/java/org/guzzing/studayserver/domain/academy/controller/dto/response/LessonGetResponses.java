package org.guzzing.studayserver.domain.academy.controller.dto.response;

import java.util.List;
import org.guzzing.studayserver.domain.academy.service.dto.result.LessonGetResults;

public record LessonGetResponses(
        List<LessonGetResponse> lessons
) {

    public static LessonGetResponses from(LessonGetResults lessonGetResults) {
        return new LessonGetResponses(
                lessonGetResults
                        .lessonGetResults()
                        .stream()
                        .map(LessonGetResponse::from)
                        .toList());
    }
}

