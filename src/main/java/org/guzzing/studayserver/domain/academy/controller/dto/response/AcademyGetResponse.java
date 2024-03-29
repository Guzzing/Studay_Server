package org.guzzing.studayserver.domain.academy.controller.dto.response;

import java.util.List;
import org.guzzing.studayserver.domain.academy.facade.dto.AcademyDetailFacadeResult;

public record AcademyGetResponse(
        String academyName,
        String contact,
        String address,
        String shuttleAvailability,
        Long expectedFee,
        String updatedDate,
        List<String> categories,
        LessonGetResponses lessonGetResponses,
        ReviewPercentGetResponse reviewPercentGetResponse,
        boolean isLiked
) {

    public static AcademyGetResponse from(AcademyDetailFacadeResult academyGetResult) {
        return new AcademyGetResponse(
                academyGetResult.academyName(),
                academyGetResult.contact(),
                academyGetResult.fullAddress(),
                academyGetResult.shuttleAvailability(),
                academyGetResult.expectedFee(),
                academyGetResult.updatedDate(),
                academyGetResult.categories(),

                LessonGetResponses.from(academyGetResult.lessonGetResults()),
                ReviewPercentGetResponse.from(academyGetResult.reviewPercentGetResult()),
                academyGetResult.isLiked()
        );

    }

}
