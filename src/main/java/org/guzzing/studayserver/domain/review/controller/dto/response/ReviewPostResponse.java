package org.guzzing.studayserver.domain.review.controller.dto.response;

import org.guzzing.studayserver.domain.review.service.dto.response.ReviewPostResult;

public record ReviewPostResponse(
        Long reviewId,
        Long academyId
) {

    public static ReviewPostResponse from(final ReviewPostResult result) {
        return new ReviewPostResponse(result.reviewId(), result.academyId());
    }

}
