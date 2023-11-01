package org.guzzing.studayserver.domain.region.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.guzzing.studayserver.domain.region.service.dto.beopjungdong.SidoResult;
import org.guzzing.studayserver.domain.region.service.dto.beopjungdong.SigunguResult;
import org.guzzing.studayserver.domain.region.service.dto.beopjungdong.UpmyeondongResult;
import org.guzzing.studayserver.domain.region.service.dto.location.RegionResult;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@ActiveProfiles({"dev", "oauth"})
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
        assertThat(result.sigunguCount()).isPositive();
    }

    @Test
    @DisplayName("시도, 시군구를 받아 해당 시도군구의 읍면동을 반환한다.")
    void findUpmyeondongBySidoAndSigungu_SidoAndSigungu_UpmyeondongResult() {
        // Given
        final String sido = "서울특별시";
        final String sigungu = "중구";

        // When
        UpmyeondongResult result = regionService.findUpmyeondongBySidoAndSigungu(sido, sigungu);

        // Then
        assertThat(result.sido()).isEqualTo(sido);
        assertThat(result.sigungu()).isEqualTo(sigungu);
        assertThat(result.upmyeondongCount()).isPositive();
    }

    @Test
    @DisplayName("조회 가능한 시도를 반환한다.")
    void findSido_None_SidoResult() {
        // Given & When
        SidoResult result = regionService.findSido();

        // Then
        assertThat(result.nation()).isEqualTo("전국");
        assertThat(result.sidos()).isNotEmpty();
        assertThat(result.sidoCount()).isEqualTo(2);
    }

    @Test
    @DisplayName("시도, 시군구, 읍면동 데이터를 요청받아, 해당하는 위경도를 반환한다.")
    void findLocation_AllAddress_RegionResult() {
        // Given
        final String sido = "서울특별시";
        final String sigungu = "중구";
        final String upmyeondong = "정동";

        // When
        RegionResult result = regionService.findLocation(sido, sigungu, upmyeondong);

        // Then
        assertThat(result.sido()).isEqualTo(sido);
        assertThat(result.upmyeondong()).isEqualTo(upmyeondong);
        assertThat(result.latitude()).isLessThanOrEqualTo(40.0);
        assertThat(result.longtigute()).isLessThanOrEqualTo(130.0);
    }

}