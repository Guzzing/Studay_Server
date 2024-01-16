package org.guzzing.studayserver.domain.review.model;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import org.guzzing.studayserver.domain.review.service.dto.request.ReviewPostParam;

public enum ReviewType {
    KINDNESS,
    GOOD_FACILITY,
    CHEAP_FEE,
    GOOD_MANAGEMENT,
    LOVELY_TEACHING,
    SHUTTLE_AVAILABILITY;

    private static final int MAX_REVIEW_COUNT = 3;

    public static Map<ReviewType, Boolean> getSelectedReviewMap(final ReviewPostParam reviewPostParam) {
        Map<ReviewType, Boolean> selectedReviewMap = new ConcurrentHashMap<>();

        selectedReviewMap.put(ReviewType.KINDNESS, reviewPostParam.kindness());
        selectedReviewMap.put(ReviewType.GOOD_FACILITY, reviewPostParam.goodFacility());
        selectedReviewMap.put(ReviewType.CHEAP_FEE, reviewPostParam.cheapFee());
        selectedReviewMap.put(ReviewType.GOOD_MANAGEMENT, reviewPostParam.goodManagement());
        selectedReviewMap.put(ReviewType.LOVELY_TEACHING, reviewPostParam.lovelyTeaching());
        selectedReviewMap.put(ReviewType.SHUTTLE_AVAILABILITY, reviewPostParam.shuttleAvailability());

        validateThreeReviewLimit(selectedReviewMap);

        return selectedReviewMap;
    }

    public static Map<ReviewType, Boolean> convertToReviewTypeMap(final Map<String, Boolean> map) {
        return Arrays.stream(ReviewType.values())
                .collect(Collectors.toMap(
                        reviewType -> reviewType,
                        reviewType -> map.get(reviewType.name())
                ));
    }

    public static Map<ReviewType, Integer> convertToReviewTypeCountMap(final Map<ReviewType, Boolean> map) {
        return Arrays.stream(ReviewType.values())
                .collect(Collectors.toMap(
                        reviewType -> reviewType,
                        reviewType -> Boolean.TRUE.equals(map.get(reviewType)) ? 1 : 0
                ));
    }

    private static void validateThreeReviewLimit(final Map<ReviewType, Boolean> map) {
        long count = map.values().stream()
                .filter(value -> value)
                .count();

        if (count > MAX_REVIEW_COUNT) {
            throw new IllegalArgumentException("리뷰는 3개 까지 가능합니다.");
        }
    }

}
