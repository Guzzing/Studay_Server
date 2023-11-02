package org.guzzing.studayserver.domain.academy.service.dto;

import org.guzzing.studayserver.domain.academy.model.Academy;
import org.guzzing.studayserver.domain.academy.model.Lesson;
import org.guzzing.studayserver.domain.academy.model.ReviewCount;

import java.util.List;

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
    public static AcademyGetResult of(Academy academy,
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

                LessonGetResults.of(lessons),
                ReviewPercentGetResult.of(reviewCount)
        );

    }

}
