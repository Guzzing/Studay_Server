package org.guzzing.studayserver.domain.academy.service.dto.result;

import java.util.List;
import org.guzzing.studayserver.domain.academy.model.Lesson;

public record LessonGetResults(
        List<LessonGetResult> lessonGetResults
) {

    public static LessonGetResults from(List<Lesson> lessons) {
        return new LessonGetResults(
                lessons
                        .stream()
                        .map(LessonGetResult::from)
                        .toList());
    }

}
