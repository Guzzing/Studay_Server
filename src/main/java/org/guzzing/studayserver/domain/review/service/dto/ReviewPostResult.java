package org.guzzing.studayserver.domain.review.service.dto;

import static org.guzzing.studayserver.domain.review.model.ReviewType.CHEAP_FEE;
import static org.guzzing.studayserver.domain.review.model.ReviewType.GOOD_FACILITY;
import static org.guzzing.studayserver.domain.review.model.ReviewType.GOOD_MANAGEMENT;
import static org.guzzing.studayserver.domain.review.model.ReviewType.KINDNESS;
import static org.guzzing.studayserver.domain.review.model.ReviewType.LOVELY_TEACHING;

import java.util.List;
import java.util.Map;
import org.guzzing.studayserver.domain.review.model.Review;
import org.guzzing.studayserver.domain.review.model.ReviewType;

public record ReviewPostResult(
        Long reviewId,
        Long memberId,
        Long academyId,
        boolean kindness,
        boolean cheapFee,
        boolean goodFacility,
        boolean goodManagement,
        boolean lovelyTeaching
) {

    public static ReviewPostResult from(final Review entity) {
        List<ReviewType> reviewTypes = entity.getReviewTypes();
        Map<ReviewType, Boolean> reviewMap = ReviewType.convertReviewListToReviewMap(reviewTypes);

        return new ReviewPostResult(
                entity.getId(),
                entity.getMemberId(),
                entity.getAcademyId(),
                reviewMap.get(KINDNESS),
                reviewMap.get(CHEAP_FEE),
                reviewMap.get(GOOD_FACILITY),
                reviewMap.get(GOOD_MANAGEMENT),
                reviewMap.get(LOVELY_TEACHING));
    }

}
