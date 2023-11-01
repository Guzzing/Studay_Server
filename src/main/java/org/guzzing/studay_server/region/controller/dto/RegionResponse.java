package org.guzzing.studay_server.region.controller.dto;

import java.text.MessageFormat;
import java.util.List;
import org.guzzing.studay_server.region.service.dto.SigunguResult;
import org.guzzing.studay_server.region.service.dto.UpmyeondongResult;

public record RegionResponse(
        String targetRegion,
        List<String> subRegion,
        int subRegionCount
) {

    public static RegionResponse from(final SigunguResult sigunguResult) {
        return new RegionResponse(sigunguResult.sido(), sigunguResult.sigungus(), sigunguResult.sigunguCount());
    }

    public static RegionResponse from(final UpmyeondongResult upmyeondongResult) {
        return new RegionResponse(
                MessageFormat.format("{0} {1}", upmyeondongResult.sido(), upmyeondongResult.sigungu()),
                upmyeondongResult.upmyeondongs(),
                upmyeondongResult.upmyeondongCount());
    }

}
