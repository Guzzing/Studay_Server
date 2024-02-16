package org.guzzing.studayserver.domain.region.service.dto.location;

public record RegionGetNameParam(
    double latitude,
    double longitude
) {
    public static RegionGetNameParam to(
        double latitude,
        double longitude
    ) {
        return new RegionGetNameParam(
            latitude,
            longitude
        );
    }
}
