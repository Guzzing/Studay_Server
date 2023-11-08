package org.guzzing.studayserver.domain.review.controller.dto.response;

import org.guzzing.studayserver.domain.review.service.dto.response.ReviewableResult;

public record ReviewableResponse(
        long academyId,
        boolean reviewable
) {

    public static ReviewableResponse from(final ReviewableResult result) {
        return new ReviewableResponse(
                result.academyId(),
                result.reviewable());
    }

}
