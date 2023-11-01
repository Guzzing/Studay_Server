package org.guzzing.studay_server.region.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.guzzing.studay_server.region.service.dto.SigunguResult;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class RegionServiceTest {

    @Autowired
    private RegionService regionService;

    @Test
    @DisplayName("시도를 받아 해당 시도의 시군구를 반환한다.")
    void findSigungusBySido_Sido_SigunguResult() {
        // Given
        final String sido = "경기도";

        // When
        SigunguResult result = regionService.findSigungusBySido(sido);

        // Then
        assertThat(result.sido()).isEqualTo(sido);
        assertThat(result.sigunguCount()).isGreaterThan(0);
    }

}