package org.guzzing.studayserver.domain.academy.controller.dto.response;

import org.guzzing.studayserver.domain.academy.service.dto.LessonGetResults;

import java.util.List;

public record LessonGetResponses(
        List<LessonGetResponse> lessons
) {
    public static LessonGetResponses of(LessonGetResults lessonGetResults) {
        return new LessonGetResponses(
                lessonGetResults
                        .lessonGetResults()
                        .stream()
                        .map(lesson -> LessonGetResponse.of(lesson))
                        .toList());
    }
}

