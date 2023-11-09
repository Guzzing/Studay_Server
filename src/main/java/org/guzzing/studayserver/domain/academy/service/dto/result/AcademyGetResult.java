package org.guzzing.studayserver.domain.academy.service.dto.result;

import java.util.List;
import org.guzzing.studayserver.domain.academy.model.Academy;
import org.guzzing.studayserver.domain.academy.model.Lesson;
import org.guzzing.studayserver.domain.academy.model.ReviewCount;

public record AcademyGetResult(
        String academyName,
        String contact,
        String fullAddress,
        String shuttleAvailability,
        Long expectedFee,
        String updatedDate,
        String areaOfExpertise,
        LessonGetResults lessonGetResults,
        ReviewPercentGetResult reviewPercentGetResult,
        boolean isLiked
) {

    public static AcademyGetResult from(
            Academy academy,
            List<Lesson> lessons,
            ReviewCount reviewCount,
            boolean isLiked) {
        return new AcademyGetResult(
                academy.getAcademyName(),
                academy.getContact(),
                academy.getFullAddress(),
                academy.getShuttleAvailability(),
                academy.getMaxEducationFee(),
                academy.getUpdatedDate().toString(),
                academy.getAreaOfExpertise(),

                LessonGetResults.from(lessons),
                ReviewPercentGetResult.from(reviewCount),

                isLiked
        );
    }

}
