package org.guzzing.studayserver.domain.review.controller.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.guzzing.studayserver.domain.review.service.dto.request.ReviewPostParam;

public record ReviewPostRequest(
        @Positive Long academyId,
        @NotNull boolean kindness,
        @NotNull boolean cheapFee,
        @NotNull boolean goodFacility,
        @NotNull boolean goodManagement,
        @NotNull boolean lovelyTeaching
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
