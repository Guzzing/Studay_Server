package org.guzzing.studayserver.domain.academy.service.dto.result;

import java.util.List;
import org.guzzing.studayserver.domain.academy.model.Academy;
import org.guzzing.studayserver.domain.academy.model.Lesson;
import org.guzzing.studayserver.domain.academy.model.ReviewCount;
import org.guzzing.studayserver.domain.academy.util.CategoryInfo;

public record AcademyGetResult(
        String academyName,
        String contact,
        String fullAddress,
        String shuttleAvailability,
        Long expectedFee,
        String updatedDate,
        LessonGetResults lessonGetResults,
        ReviewPercentGetResult reviewPercentGetResult,
        List<String> categories
) {

    public static AcademyGetResult from(
            Academy academy,
            List<Lesson> lessons,
            ReviewCount reviewCount,
            List<Long> categories) {
        return new AcademyGetResult(
                academy.getAcademyName(),
                academy.getContact(),
                academy.getFullAddress(),
                academy.getShuttleAvailability(),
                academy.getMaxEducationFee(),
                academy.getUpdatedDate().toString(),

                LessonGetResults.from(lessons),
                ReviewPercentGetResult.from(reviewCount),

                categories.stream()
                        .map(categoryId -> CategoryInfo.getCategoryNameById(categoryId))
                        .toList()
        );
    }

}
