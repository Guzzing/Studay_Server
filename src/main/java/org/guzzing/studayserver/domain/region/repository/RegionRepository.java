package org.guzzing.studayserver.domain.region.repository;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import org.guzzing.studayserver.domain.region.model.Region;
import org.locationtech.jts.geom.Point;

public interface RegionRepository {

    List<String> findSigunguBySido(final String sido);

    List<String> findUpmyeondongBySidoAndSigungu(final String sido, final String sigungu);

    Optional<Region> findBySidoAndSigunguAndUpmyeondong(final String sido, final String sigungu,
            final String upmyeondong);

    default Region getBySidoAndSigunguAndUpmyeondong(final String sido, final String sigungu,
            final String upmyeondong) {
        return this.findBySidoAndSigunguAndUpmyeondong(sido, sigungu, upmyeondong)
                .orElseThrow(() -> new EntityNotFoundException("해당 지역에 해당하는 법정동 지역이 없습니다."));
    }

    Optional<Region> findByAreaContainingPoint(final Point point);

    default Region getByAreaContainingPoint(final Point point) {
        return this.findByAreaContainingPoint(point)
                .orElseThrow(() -> new EntityNotFoundException("해당 위경도에 해당하는 법정동 지역이 없습니다."));
    }

    Region save(Region region);
}
