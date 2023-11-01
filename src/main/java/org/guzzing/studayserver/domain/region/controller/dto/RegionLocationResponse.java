package org.guzzing.studayserver.domain.region.controller.dto;

import org.guzzing.studayserver.domain.region.service.dto.location.RegionResult;

public record RegionLocationResponse(
        String sido,
        String sigungu,
        String upmyeondong,
        double latitute,
        double longitute
) {

    public static RegionLocationResponse from(final RegionResult regionResult) {
        return new RegionLocationResponse(
                regionResult.sido(),
                regionResult.sigungu(),
                regionResult.upmyeondong(),
                regionResult.latitude(),
                regionResult.longtigute());
    }

}
