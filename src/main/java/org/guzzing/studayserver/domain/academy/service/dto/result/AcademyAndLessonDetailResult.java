package org.guzzing.studayserver.domain.academy.service.dto.result;

import java.util.List;
import org.guzzing.studayserver.domain.academy.model.Lesson;
import org.guzzing.studayserver.domain.academy.util.CategoryInfo;

public record AcademyAndLessonDetailResult(
        String academyName,
        String address,
        String lessonName,
        Integer capacity,
        Long totalFee,
        List<String> categories
) {

    public static AcademyAndLessonDetailResult from(Lesson lesson, List<Long> categoryIds) {
        return new AcademyAndLessonDetailResult(
                lesson.getAcademy().getAcademyName(),
                lesson.getAcademy().getFullAddress(),
                lesson.getCurriculum(),
                lesson.getCapacity(),
                lesson.getTotalFee(),
                categoryIds.stream()
                        .map(categoryId -> CategoryInfo.getCategoryNameById(categoryId))
                        .toList()
        );
    }
}
