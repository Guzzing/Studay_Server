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

    public static Map<NewReviewType, Integer> newReviewCountOf(final List<String> list) {
        return Arrays.stream(NewReviewType.values())
                .collect(Collectors.toMap(
                        reviewType -> reviewType,
                        reviewType -> list.contains(reviewType.name()) ? 1 : 0
                ));
    }
}
