package org.guzzing.studayserver.domain.academy.facade.dto;

import java.util.List;
import org.guzzing.studayserver.domain.academy.service.dto.result.AcademyGetResult;
import org.guzzing.studayserver.domain.academy.service.dto.result.LessonGetResults;
import org.guzzing.studayserver.domain.academy.service.dto.result.ReviewPercentGetResult;

public record AcademyDetailFacadeResult(
        String academyName,
        String contact,
        String fullAddress,
        String shuttleAvailability,
        Long expectedFee,
        String updatedDate,
        LessonGetResults lessonGetResults,
        ReviewPercentGetResult reviewPercentGetResult,
        List<String> categories,
        boolean isLiked
) {

    public static AcademyDetailFacadeResult of(AcademyGetResult academyGetResult, boolean isLiked) {
        return new AcademyDetailFacadeResult(
                academyGetResult.academyName(),
                academyGetResult.contact(),
                academyGetResult.fullAddress(),
                academyGetResult.shuttleAvailability(),
                academyGetResult.expectedFee(),
                academyGetResult.updatedDate(),
                academyGetResult.lessonGetResults(),
                academyGetResult.reviewPercentGetResult(),
                academyGetResult.categories(),
                isLiked
        );
    }

}
