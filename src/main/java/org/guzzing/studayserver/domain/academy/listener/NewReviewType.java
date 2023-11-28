package org.guzzing.studayserver.domain.academy.listener;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public enum NewReviewType {
    KINDNESS,
    GOOD_FACILITY,
    CHEAP_FEE,
    GOOD_MANAGEMENT,
    LOVELY_TEACHING,
    SHUTTLE_AVAILABILITY;

    public static Map<NewReviewType, Integer> newReviewCountOf(final Map<String, Boolean> map) {
        return Arrays.stream(NewReviewType.values())
                .collect(Collectors.toMap(
                        reviewType -> reviewType,
                        reviewType -> map.get(reviewType.name()) ? 1 : 0
                ));
    }
}
