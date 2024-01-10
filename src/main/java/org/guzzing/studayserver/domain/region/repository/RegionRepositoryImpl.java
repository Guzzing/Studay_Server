package org.guzzing.studayserver.domain.region.repository;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import org.guzzing.studayserver.domain.region.model.Region;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Repository;

@Repository
public class RegionRepositoryImpl implements RegionRepository {

    private final RegionJpaRepository regionJpaRepository;

    public RegionRepositoryImpl(RegionJpaRepository regionJpaRepository) {
        this.regionJpaRepository = regionJpaRepository;
    }

    @Override
    public List<String> findSigunguBySido(String sido) {
        return regionJpaRepository.findSigunguBySido(sido);
    }

    @Override
    public List<String> findUpmyeondongBySidoAndSigungu(String sido, String sigungu) {
        return regionJpaRepository.findUpmyeondongBySidoAndSigungu(sido, sigungu);
    }

    @Override
    public Optional<Region> findBySidoAndSigunguAndUpmyeondong(String sido, String sigungu,
            String upmyeondong) {
        return regionJpaRepository.findBySidoAndSigunguAndUpmyeondong(sido, sigungu, upmyeondong);
    }

    @Override
    public Region getBySidoAndSigunguAndUpmyeondong(String sido, String sigungu, String upmyeondong) {
        return regionJpaRepository.findBySidoAndSigunguAndUpmyeondong(sido, sigungu, upmyeondong)
                .orElseThrow(() -> new EntityNotFoundException("해당 지역에 해당하는 법정동 지역이 없습니다."));
    }

    @Override
    public Optional<Region> findByAreaContainingPoint(Point point) {
        return regionJpaRepository.findByAreaContainingPoint(point);
    }

    @Override
    public Region getByAreaContainingPoint(Point point) {
        return regionJpaRepository.findByAreaContainingPoint(point)
                .orElseThrow(() -> new EntityNotFoundException("해당 위경도에 해당하는 법정동 지역이 없습니다."));
    }

    @Override
    public Region save(Region region) {
        return regionJpaRepository.save(region);
    }
}
