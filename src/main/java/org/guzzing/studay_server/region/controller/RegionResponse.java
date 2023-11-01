package org.guzzing.studay_server.region.controller;

import java.util.List;
import org.guzzing.studay_server.region.service.SigunguResult;

public record RegionResponse(
        String targetRegion,
        List<String> subRegion,
        int subRegionCount
) {

    public static RegionResponse from(final SigunguResult sigunguResult) {
        return new RegionResponse(sigunguResult.sido(), sigunguResult.sigungus(), sigunguResult.sigunguCount());
    }

}
