package org.guzzing.studayserver.domain.region.repository;

import java.util.List;
import java.util.Optional;
import org.guzzing.studayserver.domain.region.model.Region;
import org.locationtech.jts.geom.Point;

public interface RegionRepository {

    List<String> findSigunguBySido(final String sido);

    List<String> findUpmyeondongBySidoAndSigungu(final String sido, final String sigungu);

    Optional<Region> findBySidoAndSigunguAndUpmyeondong(final String sido, final String sigungu,
            final String upmyeondong);

    Region getBySidoAndSigunguAndUpmyeondong(final String sido, final String sigungu, final String upmyeondong);

    Optional<Region> findByAreaContainingPoint(final Point point);

    Region getByAreaContainingPoint(final Point point);

    Region save(Region region);
}
