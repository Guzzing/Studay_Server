package org.guzzing.studayserver.domain.review.controller.dto;

import org.guzzing.studayserver.domain.review.service.dto.ReviewPostResult;

public record ReviewPostResponse(
        Long reviewId,
        Long academyId
) {

    public static ReviewPostResponse from(final ReviewPostResult result) {
        return new ReviewPostResponse(result.reviewId(), result.academyId());
    }

}
