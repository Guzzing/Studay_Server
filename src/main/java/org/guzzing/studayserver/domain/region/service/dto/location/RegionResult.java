package org.guzzing.studayserver.domain.region.service.dto.location;

import org.guzzing.studayserver.domain.region.model.Region;
import org.locationtech.jts.geom.MultiPolygon;
import org.locationtech.jts.geom.Point;

public record RegionResult(
        long id,
        String sido,
        String sigungu,
        String upmyeondong,
        Point point,
        MultiPolygon area
) {

    public static RegionResult from(final Region region) {
        return new RegionResult(
                region.getId(),
                region.getSido(),
                region.getSigungu(),
                region.getUpmyeondong(),
                region.getPoint(),
                region.getArea());
    }

}
