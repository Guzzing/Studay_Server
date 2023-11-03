package org.guzzing.studayserver.domain.region.service.dto.location;

import org.guzzing.studayserver.domain.region.model.Region;

public record RegionResult(
        String sido,
        String sigungu,
        String upmyeondong,
        double latitude,
        double longtigute
) {

    public static RegionResult from(final Region region) {
        return new RegionResult(
                region.getSido(),
                region.getSigungu(),
                region.getUpmyeondong(),
                region.getLatitude(),
                region.getLongitude());
    }

}
