package org.guzzing.studayserver.domain.academy.service.dto;

import org.guzzing.studayserver.domain.academy.model.ReviewCount;

public record ReviewPercentGetResult(
        int kindnessPercent ,
        int goodFacilityPercent ,
        int cheapFeePercent,
        int goodManagementPercent,
        int lovelyTeachingPercent
) {
    public static ReviewPercentGetResult from(ReviewCount reviewCount) {
        return new ReviewPercentGetResult(
                reviewCount.makePercent(reviewCount.getKindnessCount()),
                reviewCount.makePercent(reviewCount.getGoodFacilityCount()),
                reviewCount.makePercent(reviewCount.getCheapFeeCount()),
                reviewCount.makePercent(reviewCount.getGoodManagementCount()),
                reviewCount.makePercent(reviewCount.getLovelyTeachingCount())
        );
    }
}
