package org.guzzing.studayserver.domain.region.service;

import static org.guzzing.studayserver.domain.region.model.Region.BASE_REGION_SIDO;

import com.amazonaws.regions.Regions;
import java.util.List;
import org.guzzing.studayserver.domain.region.model.Region;
import org.guzzing.studayserver.domain.region.repository.RegionRepository;
import org.guzzing.studayserver.domain.region.service.dto.beopjungdong.SidoResult;
import org.guzzing.studayserver.domain.region.service.dto.beopjungdong.SigunguResult;
import org.guzzing.studayserver.domain.region.service.dto.beopjungdong.UpmyeondongResult;
import org.guzzing.studayserver.domain.region.service.dto.location.RegionResult;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class RegionService {

    private final RegionRepository regionRepository;

    public RegionService(final RegionRepository regionRepository) {
        this.regionRepository = regionRepository;
    }

    public SigunguResult findSigungusBySido(final String sido) {
        List<String> sigungu = regionRepository.findSigunguBySido(sido);
        return SigunguResult.from(sido, sigungu);
    }

    public UpmyeondongResult findUpmyeondongBySidoAndSigungu(final String sido, final String sigungu) {
        List<String> upmyeondong = regionRepository.findUpmyeondongBySidoAndSigungu(sido, sigungu);
        return UpmyeondongResult.from(sido, sigungu, upmyeondong);
    }

    public SidoResult findSido() {
        return SidoResult.from(BASE_REGION_SIDO);
    }

    public RegionResult findLocation(final String sido, final String sigungu, final String upmyeondong) {
        Region region = regionRepository.getBySidoAndSigunguAndUpmyeondong(sido, sigungu, upmyeondong);
        return RegionResult.from(region);
    }

    public RegionResult findAreaContainningPoint(final Point point) {
        Region region = regionRepository.getRegionsContainingPoint(point);

        return RegionResult.from(region);
    }

}
