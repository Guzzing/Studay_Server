package org.guzzing.studayserver.testutil.fixture;

import static org.guzzing.studayserver.domain.review.model.ReviewType.CHEAP_FEE;
import static org.guzzing.studayserver.domain.review.model.ReviewType.GOOD_FACILITY;
import static org.guzzing.studayserver.domain.review.model.ReviewType.GOOD_MANAGEMENT;
import static org.guzzing.studayserver.domain.review.model.ReviewType.KINDNESS;
import static org.guzzing.studayserver.domain.review.model.ReviewType.LOVELY_TEACHING;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.guzzing.studayserver.domain.review.controller.dto.ReviewPostRequest;
import org.guzzing.studayserver.domain.review.model.ReviewType;
import org.guzzing.studayserver.domain.review.service.dto.ReviewPostParam;

public class ReviewFixture {

    public static final Long memberId = 1L;
    public static final Long academyId = 1L;

    public static ReviewPostParam makeReviewPostParam(boolean isValid) {
        Map<ReviewType, Boolean> reviewMap = isValid ? makeValidReviewMap() : makeInvalidReviewMap();

        ReviewPostRequest request = makeReviewPostRequest(reviewMap);

        return ReviewPostRequest.to(1L, request);
    }

    public static Map<ReviewType, Boolean> makeInvalidReviewMap() {
        Map<ReviewType, Boolean> invalidReviewMap = new ConcurrentHashMap<>();

        invalidReviewMap.put(KINDNESS, true);
        invalidReviewMap.put(CHEAP_FEE, true);
        invalidReviewMap.put(GOOD_FACILITY, true);
        invalidReviewMap.put(GOOD_MANAGEMENT, false);
        invalidReviewMap.put(LOVELY_TEACHING, true);

        return invalidReviewMap;
    }

    public static Map<ReviewType, Boolean> makeValidReviewMap() {
        Map<ReviewType, Boolean> validReviewMap = new ConcurrentHashMap<>();

        validReviewMap.put(KINDNESS, true);
        validReviewMap.put(CHEAP_FEE, true);
        validReviewMap.put(GOOD_FACILITY, false);
        validReviewMap.put(GOOD_MANAGEMENT, false);
        validReviewMap.put(LOVELY_TEACHING, true);

        return validReviewMap;
    }

    public static ReviewPostRequest makeReviewPostRequest(Map<ReviewType, Boolean> invalidReviewMap) {
        return new ReviewPostRequest(
                1L,
                invalidReviewMap.get(KINDNESS),
                invalidReviewMap.get(CHEAP_FEE),
                invalidReviewMap.get(GOOD_FACILITY),
                invalidReviewMap.get(GOOD_MANAGEMENT),
                invalidReviewMap.get(LOVELY_TEACHING));
    }

}
