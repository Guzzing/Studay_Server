package org.guzzing.studayserver.domain.academy.controller.dto.response;

import org.guzzing.studayserver.domain.academy.service.dto.result.ReviewPercentGetResult;

public record ReviewPercentGetResponse(
        int kindnessPercent,
        int goodFacilityPercent,
        int cheapFeePercent,
        int goodManagementPercent,
        int lovelyTeachingPercent
) {
    public static ReviewPercentGetResponse from(ReviewPercentGetResult response) {
        return new ReviewPercentGetResponse(
                response.kindnessPercent(),
                response.goodFacilityPercent(),
                response.cheapFeePercent(),
                response.goodManagementPercent(),
                response.lovelyTeachingPercent()
        );
    }
}
