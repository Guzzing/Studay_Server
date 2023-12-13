package org.guzzing.studayserver.domain.review.service.dto.response;

public record ReviewableResult(
        long memberId,
        long academyId,
        boolean reviewable
) {

    public static ReviewableResult of(final long memberId, final long academyId, final boolean reviewable) {
        return new ReviewableResult(memberId, academyId, reviewable);
    }

}
