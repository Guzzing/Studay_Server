package org.guzzing.studayserver.domain.region.repository;

import java.awt.Point;
import java.util.List;
import java.util.Optional;
import org.guzzing.studayserver.domain.region.model.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RegionJpaRepository extends JpaRepository<Region, Long>, RegionRepository {

    @Query("select distinct(r.address.sigungu) from Region r where r.address.sido = :sido")
    List<String> findSigunguBySido(final String sido);

    @Query("select r.address.upmyeondong from Region r where r.address.sido = :sido and r.address.sigungu = :sigungu")
    List<String> findUpmyeondongBySidoAndSigungu(final String sido, final String sigungu);

    @Query("select r from Region r where r.address.sido = :sido and r.address.sigungu = :sigungu and r.address.upmyeondong = :upmyeondong")
    Optional<Region> findBySidoAndSigunguAndUpmyeondong(final String sido, final String sigungu,
            final String upmyeondong);

    @Query("select r from Region r where ST_Contains(r.area, ?1) = true")
    Optional<Region> findRegionsContainingPoint(final Point point);

}
