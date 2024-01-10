package org.guzzing.studayserver.domain.region.repository;

import java.util.List;
import java.util.Optional;
import org.guzzing.studayserver.domain.region.model.Region;
import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RegionJpaRepository extends JpaRepository<Region, Long> {

    @Query("select distinct(r.address.sigungu) from Region r where r.address.sido = :sido")
    List<String> findSigunguBySido(final String sido);

    @Query("select distinct(r.address.upmyeondong) from Region r where r.address.sido = :sido and r.address.sigungu = :sigungu")
    List<String> findUpmyeondongBySidoAndSigungu(final String sido, final String sigungu);

    @Query("select r from Region r where r.address.sido = :sido and r.address.sigungu = :sigungu and r.address.upmyeondong = :upmyeondong")
    Optional<Region> findBySidoAndSigunguAndUpmyeondong(final String sido, final String sigungu,
            final String upmyeondong);

    @Query("select r from Region r where ST_Contains(r.area, :point)")
    Optional<Region> findByAreaContainingPoint(@Param("point") final Point point);

}
