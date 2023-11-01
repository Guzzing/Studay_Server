package org.guzzing.studay_server.region.service;

import java.util.List;
import org.guzzing.studay_server.region.repository.RegionRepository;
import org.guzzing.studay_server.region.service.dto.SigunguResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class RegionService {

    private final RegionRepository regionRepository;

    public RegionService(RegionRepository regionRepository) {
        this.regionRepository = regionRepository;
    }

    public SigunguResult findSigungusBySido(final String sido) {
        List<String> sigungu = regionRepository.findSigunguBySido(sido);
        return SigunguResult.from(sido, sigungu);
    }
}
