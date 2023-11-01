package org.guzzing.studayserver.domain.region.service;

import java.util.List;
import org.guzzing.studayserver.domain.region.controller.SidoResult;
import org.guzzing.studayserver.domain.region.repository.RegionRepository;
import org.guzzing.studayserver.domain.region.service.dto.SigunguResult;
import org.guzzing.studayserver.domain.region.service.dto.UpmyeondongResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class RegionService {

    private static final List<String> BASE_REGION_SIDO = List.of("서울특별시", "경기도");

    private final RegionRepository regionRepository;

    public RegionService(RegionRepository regionRepository) {
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
}
