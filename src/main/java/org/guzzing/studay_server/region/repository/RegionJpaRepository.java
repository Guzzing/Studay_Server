package org.guzzing.studay_server.region.repository;

import java.util.List;
import org.guzzing.studay_server.region.model.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RegionJpaRepository extends JpaRepository<Region, Long>, RegionRepository {

    @Query("select distinct(r.sigungu) from Region r where r.sido = :sido")
    List<String> findSigunguBySido(final String sido);

}
