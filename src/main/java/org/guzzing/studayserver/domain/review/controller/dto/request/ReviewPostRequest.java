package org.guzzing.studayserver.domain.review.controller.dto.request;

import jakarta.validation.constraints.Positive;
import org.guzzing.studayserver.domain.review.service.dto.request.ReviewPostParam;

public record ReviewPostRequest(
        @Positive Long academyId,
        boolean kindness,
        boolean cheapFee,
        boolean goodFacility,
        boolean goodManagement,
        boolean lovelyTeaching
) {

    public static ReviewPostParam to(final Long memberId, final ReviewPostRequest request) {
        return new ReviewPostParam(
                memberId,
                request.academyId(),
                request.kindness(),
                request.cheapFee(),
                request.goodFacility(),
                request.goodManagement(),
                request.lovelyTeaching()
        );
    }

}
