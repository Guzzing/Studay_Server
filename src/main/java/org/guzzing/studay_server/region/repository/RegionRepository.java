package org.guzzing.studay_server.region.repository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;

public interface RegionRepository {

    List<String> findSigunguBySido(final String sido);

    List<String> findUpmyeondongBySidoAndSigungu(final String sido, final String sigungu);
}
