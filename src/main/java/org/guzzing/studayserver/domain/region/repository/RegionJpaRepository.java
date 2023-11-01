package org.guzzing.studayserver.domain.region.repository;

import java.util.List;
import org.guzzing.studayserver.domain.region.model.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RegionJpaRepository extends JpaRepository<Region, Long>, RegionRepository {

    @Query("select distinct(r.sigungu) from Region r where r.sido = :sido")
    List<String> findSigunguBySido(final String sido);

    @Query("select r.upmyeondong from Region r where r.sido = :sido and r.sigungu = :sigungu")
    List<String> findUpmyeondongBySidoAndSigungu(final String sido, final String sigungu);

}
