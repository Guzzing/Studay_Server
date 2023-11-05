package org.guzzing.studayserver.domain.academy.controller.dto.response;

import org.guzzing.studayserver.domain.academy.service.dto.result.LessonGetResults;
import java.util.List;

public record LessonGetResponses(
        List<LessonGetResponse> lessons
) {

    public static LessonGetResponses from(LessonGetResults lessonGetResults) {
        return new LessonGetResponses(
                lessonGetResults
                        .lessonGetResults()
                        .stream()
                        .map(lesson -> LessonGetResponse.from(lesson))
                        .toList());
    }
}

