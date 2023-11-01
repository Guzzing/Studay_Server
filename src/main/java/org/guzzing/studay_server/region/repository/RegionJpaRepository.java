package org.guzzing.studay_server.region.repository;

import java.util.List;
import org.guzzing.studay_server.region.model.Region;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegionJpaRepository extends JpaRepository<Region, Long>, RegionRepository {

    List<String> findBySido(final String sido);

}
