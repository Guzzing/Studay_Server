package org.guzzing.studayserver.domain.academy.service.dto.result;

import java.util.List;

public record LessonFindByIdsResults(
        List<LessonFindByIdsResult> lessons
) {

    public record LessonFindByIdsResult(
            Long lessonId,
            String academyName,
            String lessonName
    ) {


    }
}
