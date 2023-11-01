package org.guzzing.studay_server.region.repository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;

public interface RegionRepository {

    @Query("select distinct(r.sigungu) from Region r where r.sido = :sido")
    List<String> findBySido(final String sido);

}
