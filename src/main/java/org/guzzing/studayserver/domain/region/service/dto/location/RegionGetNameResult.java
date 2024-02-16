package org.guzzing.studayserver.domain.region.service.dto.location;

import org.guzzing.studayserver.domain.region.model.Region;

public record RegionGetNameResult(
    String sido,
    String sigungu,
    String upmyeondong
) {
    public static RegionGetNameResult from(Region region) {
        return new RegionGetNameResult(
            region.getSido(),
            region.getSigungu(),
            region.getUpmyeondong()
        );
    }
}
