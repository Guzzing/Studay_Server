package org.guzzing.studayserver.domain.academy.controller.dto.response;

import org.guzzing.studayserver.domain.academy.service.dto.result.AcademyGetResult;

public record AcademyGetResponse(
        String academyName,
        String contact,
        String fullAddress,
        String shuttleAvailability,
        Long expectedFee,
        String updatedDate,
        String areaOfExpertise,
        LessonGetResponses lessonGetResponses,
        ReviewPercentGetResponse reviewPercentGetResponse
) {
    public static AcademyGetResponse from(AcademyGetResult academyGetResult) {
        return new AcademyGetResponse(
                academyGetResult.academyName(),
                academyGetResult.contact(),
                academyGetResult.fullAddress(),
                academyGetResult.shuttleAvailability(),
                academyGetResult.expectedFee(),
                academyGetResult.updatedDate(),
                academyGetResult.areaOfExpertise(),

                LessonGetResponses.from(academyGetResult.lessonGetResults()),
                ReviewPercentGetResponse.from(academyGetResult.reviewPercentGetResult())
        );

    }

}
