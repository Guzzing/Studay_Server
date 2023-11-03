package org.guzzing.studayserver.domain.academy.service.dto;

import org.guzzing.studayserver.domain.academy.model.Lesson;

import java.util.List;

public record LessonGetResults(
        List<LessonGetResult> lessonGetResults
) {
    public static LessonGetResults from(List<Lesson> lessons) {
        return new LessonGetResults(
                lessons
                        .stream()
                        .map(lesson -> LessonGetResult.from(lesson))
                        .toList());
    }
}
