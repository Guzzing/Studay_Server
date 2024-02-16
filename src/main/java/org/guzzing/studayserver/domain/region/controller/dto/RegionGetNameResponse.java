package org.guzzing.studayserver.domain.region.controller.dto;

import org.guzzing.studayserver.domain.region.service.dto.location.RegionGetNameResult;

public record RegionGetNameResponse(
    String sido,
    String sigungu,
    String upmyeondong
) {
    public static RegionGetNameResponse to(RegionGetNameResult result) {
        return new RegionGetNameResponse(
            result.sido(),
            result.sigungu(),
            result.upmyeondong()
        );
    }
}
