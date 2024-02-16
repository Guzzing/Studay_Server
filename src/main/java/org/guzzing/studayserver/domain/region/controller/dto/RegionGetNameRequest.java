package org.guzzing.studayserver.domain.region.controller.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import org.guzzing.studayserver.domain.region.service.dto.location.RegionGetNameParam;

public record RegionGetNameRequest(
    @NotNull(message = "Latitude cannot be null")
    @DecimalMin(value = "-90", message = "Invalid latitude")
    double lat,

    @NotNull(message = "Longitude cannot be null")
    @DecimalMin(value = "-180", message = "Invalid longitude")
    double lng
) {
    public RegionGetNameParam toRegionGetNameParam() {
        return new RegionGetNameParam(
            lat,
            lng
        );
    }
}
