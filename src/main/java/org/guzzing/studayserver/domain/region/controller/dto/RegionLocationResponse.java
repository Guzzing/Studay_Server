package org.guzzing.studayserver.domain.region.controller.dto;

import org.guzzing.studayserver.domain.region.service.dto.location.RegionResult;
import org.locationtech.jts.geom.MultiPolygon;
import org.locationtech.jts.geom.Point;

public record RegionLocationResponse(
        long id,
        String sido,
        String sigungu,
        String upmyeondong,
        double latitude,
        double longitude
) {

    public static RegionLocationResponse from(final RegionResult regionResult) {
        return new RegionLocationResponse(
                regionResult.id(),
                regionResult.sido(),
                regionResult.sigungu(),
                regionResult.upmyeondong(),
                regionResult.point().getX(),
                regionResult.point().getY());
    }

}
