package org.guzzing.studayserver.domain.region.repository;

import java.util.List;
import org.guzzing.studayserver.domain.region.model.Region;

public interface RegionRepository {

    List<String> findSigunguBySido(final String sido);

    List<String> findUpmyeondongBySidoAndSigungu(final String sido, final String sigungu);

    Region findBySidoAndSigunguAndUpmyeondong(final String sido, final String sigungu, final String upmyeondong);
}
