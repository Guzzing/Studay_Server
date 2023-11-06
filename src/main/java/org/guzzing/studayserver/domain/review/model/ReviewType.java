package org.guzzing.studayserver.domain.review.model;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import org.guzzing.studayserver.domain.review.service.dto.request.ReviewPostParam;
import org.guzzing.studayserver.global.exception.ReviewException;

public enum ReviewType {
    KINDNESS,
    GOOD_FACILITY,
    CHEAP_FEE,
    GOOD_MANAGEMENT,
    LOVELY_TEACHING;

    private static final int MAX_REVIEW_COUNT = 3;

    public static Map<ReviewType, Boolean> getSelectedReviewMap(final ReviewPostParam reviewPostParam) {
        Map<ReviewType, Boolean> selectedReviewMap = new ConcurrentHashMap<>();

        selectedReviewMap.put(ReviewType.KINDNESS, reviewPostParam.kindness());
        selectedReviewMap.put(ReviewType.GOOD_FACILITY, reviewPostParam.goodFacility());
        selectedReviewMap.put(ReviewType.CHEAP_FEE, reviewPostParam.cheapFee());
        selectedReviewMap.put(ReviewType.GOOD_MANAGEMENT, reviewPostParam.goodManagement());
        selectedReviewMap.put(ReviewType.LOVELY_TEACHING, reviewPostParam.lovelyTeaching());

        validateThreeReviewLimit(selectedReviewMap);

        return selectedReviewMap;
    }

    public static List<ReviewType> convertReviewMapToReviewList(final Map<ReviewType, Boolean> map) {
        return map.entrySet().stream()
                .filter(Entry::getValue)
                .map(Entry::getKey)
                .toList();
    }

    public static Map<ReviewType, Boolean> convertReviewListToReviewMap(final List<ReviewType> list) {
        return Arrays.stream(ReviewType.values())
                .collect(Collectors.toMap(
                        reviewType -> reviewType,
                        list::contains
                ));
    }

    private static void validateThreeReviewLimit(final Map<ReviewType, Boolean> map) {
        long count = map.values().stream()
                .filter(value -> value)
                .count();

        if (count > MAX_REVIEW_COUNT) {
            throw new ReviewException("리뷰는 3개까지만 가능합니다.");
        }
    }

}
