package org.guzzing.studayserver.domain.review.service.dto.response;

import static org.guzzing.studayserver.domain.review.model.ReviewType.CHEAP_FEE;
import static org.guzzing.studayserver.domain.review.model.ReviewType.GOOD_FACILITY;
import static org.guzzing.studayserver.domain.review.model.ReviewType.GOOD_MANAGEMENT;
import static org.guzzing.studayserver.domain.review.model.ReviewType.KINDNESS;
import static org.guzzing.studayserver.domain.review.model.ReviewType.LOVELY_TEACHING;

import java.util.Map;
import org.guzzing.studayserver.domain.review.model.Review;
import org.guzzing.studayserver.domain.review.model.ReviewType;

public record ReviewPostResult(
        long reviewId,
        long memberId,
        long academyId,
        boolean kindness,
        boolean cheapFee,
        boolean goodFacility,
        boolean goodManagement,
        boolean lovelyTeaching
) {

    public static ReviewPostResult from(final Review entity) {
        final Map<ReviewType, Boolean> reviewType = ReviewType.convertToReviewTypeMap(entity.getReviewType());

        return new ReviewPostResult(
                entity.getId(),
                entity.getMemberId(),
                entity.getAcademyId(),
                reviewType.get(KINDNESS),
                reviewType.get(CHEAP_FEE),
                reviewType.get(GOOD_FACILITY),
                reviewType.get(GOOD_MANAGEMENT),
                reviewType.get(LOVELY_TEACHING));
    }

}
