package org.guzzing.studayserver.testutil.fixture.review;

import static org.guzzing.studayserver.domain.review.model.ReviewType.CHEAP_FEE;
import static org.guzzing.studayserver.domain.review.model.ReviewType.GOOD_FACILITY;
import static org.guzzing.studayserver.domain.review.model.ReviewType.GOOD_MANAGEMENT;
import static org.guzzing.studayserver.domain.review.model.ReviewType.KINDNESS;
import static org.guzzing.studayserver.domain.review.model.ReviewType.LOVELY_TEACHING;
import static org.guzzing.studayserver.domain.review.model.ReviewType.SHUTTLE_AVAILABILITY;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.guzzing.studayserver.domain.review.controller.dto.request.ReviewPostRequest;
import org.guzzing.studayserver.domain.review.model.ReviewType;
import org.guzzing.studayserver.domain.review.service.dto.request.ReviewPostParam;

public class ReviewFixture {

    public static ReviewPostParam makeReviewPostParam(final long memberId, final long academyId,
            final Map<ReviewType, Boolean> reviewMap) {
        ReviewPostRequest request = makeReviewPostRequest(academyId, reviewMap);

        return ReviewPostRequest.to(memberId, request);
    }

    public static ReviewPostRequest makeReviewPostRequest(final long academyId,
            final Map<ReviewType, Boolean> reviewMap) {
        return new ReviewPostRequest(
                academyId,
                reviewMap.get(KINDNESS),
                reviewMap.get(CHEAP_FEE),
                reviewMap.get(GOOD_FACILITY),
                reviewMap.get(GOOD_MANAGEMENT),
                reviewMap.get(LOVELY_TEACHING),
                reviewMap.get(SHUTTLE_AVAILABILITY));
    }

    public static Map<ReviewType, Boolean> makeValidReviewMap() {
        Map<ReviewType, Boolean> validReviewMap = new ConcurrentHashMap<>();

        validReviewMap.put(KINDNESS, true);
        validReviewMap.put(CHEAP_FEE, true);
        validReviewMap.put(GOOD_FACILITY, false);
        validReviewMap.put(GOOD_MANAGEMENT, false);
        validReviewMap.put(LOVELY_TEACHING, true);
        validReviewMap.put(SHUTTLE_AVAILABILITY, false);

        return validReviewMap;
    }

    public static Map<ReviewType, Boolean> makeInvalidReviewMap() {
        Map<ReviewType, Boolean> invalidReviewMap = new ConcurrentHashMap<>();

        invalidReviewMap.put(KINDNESS, true);
        invalidReviewMap.put(CHEAP_FEE, true);
        invalidReviewMap.put(GOOD_FACILITY, true);
        invalidReviewMap.put(GOOD_MANAGEMENT, false);
        invalidReviewMap.put(LOVELY_TEACHING, true);
        invalidReviewMap.put(SHUTTLE_AVAILABILITY, false);

        return invalidReviewMap;
    }

}
