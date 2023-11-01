package org.guzzing.studayserver.domain.region.controller.dto;

import java.text.MessageFormat;
import java.util.List;
import org.guzzing.studayserver.domain.region.service.dto.beopjungdong.SidoResult;
import org.guzzing.studayserver.domain.region.service.dto.beopjungdong.SigunguResult;
import org.guzzing.studayserver.domain.region.service.dto.beopjungdong.UpmyeondongResult;

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

    public static RegionResponse from(final SidoResult sidoResult) {
        return new RegionResponse(sidoResult.nation(), sidoResult.sidos(), sidoResult.sidoCount());
    }

}
