package org.guzzing.studayserver.domain.academy.service.dto;

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
        ReviewPercentGetResult reviewPercentGetResult
) {

    public static AcademyGetResult from(Academy academy,
            List<Lesson> lessons,
            ReviewCount reviewCount) {
        return new AcademyGetResult(
                academy.getName(),
                academy.getContact(),
                academy.getAddress(),
                academy.getShuttleAvailability(),
                academy.getMaxEducationFee(),
                academy.getUpdatedDate().toString(),
                academy.getAreaOfExpertise(),

                LessonGetResults.from(lessons),
                ReviewPercentGetResult.from(reviewCount)
        );

    }

}
