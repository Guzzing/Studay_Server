package org.guzzing.studayserver.domain.review.service.dto.response;

public record ReviewableResult(
        Long memberId,
        Long academyId,
        boolean reviewable
) {

    public static ReviewableResult of(final Long memberId, final Long academyId, final boolean reviewable) {
        return new ReviewableResult(memberId, academyId, reviewable);
    }

}
