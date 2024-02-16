package org.guzzing.studayserver.domain.region.service.dto.location;

import org.guzzing.studayserver.domain.region.controller.dto.RegionGetNameRequest;

public record RegionGetNameParam(
    double latitude,
    double longitude
) {
}
