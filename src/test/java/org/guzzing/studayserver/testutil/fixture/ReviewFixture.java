package org.guzzing.studayserver.testutil.fixture;

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

    public static final Long memberId = 1L;
    public static final Long academyId = 1L;

    public static ReviewPostParam makeReviewPostParam(boolean isValid) {
        ReviewPostRequest request = makeReviewPostRequest(isValid);

        return ReviewPostRequest.to(1L, request);
    }

    public static ReviewPostRequest makeReviewPostRequest(boolean isValid) {
        Map<ReviewType, Boolean> reviewMap = isValid ? makeValidReviewMap() : makeInvalidReviewMap();

        return new ReviewPostRequest(
                1L,
                reviewMap.get(KINDNESS),
                reviewMap.get(CHEAP_FEE),
                reviewMap.get(GOOD_FACILITY),
                reviewMap.get(GOOD_MANAGEMENT),
                reviewMap.get(LOVELY_TEACHING),
                reviewMap.get(SHUTTLE_AVAILABILITY));
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

}
