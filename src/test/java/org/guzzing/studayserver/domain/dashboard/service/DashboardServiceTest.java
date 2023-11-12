package org.guzzing.studayserver.domain.dashboard.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.guzzing.studayserver.domain.academy.service.AcademyAccessService;
import org.guzzing.studayserver.domain.dashboard.fixture.DashboardFixture;
import org.guzzing.studayserver.domain.dashboard.repository.DashboardRepository;
import org.guzzing.studayserver.domain.dashboard.service.dto.request.DashboardPostParam;
import org.guzzing.studayserver.domain.dashboard.service.dto.response.DashboardResult;
import org.guzzing.studayserver.domain.member.service.MemberAccessService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class DashboardServiceTest {

    @Autowired
    private DashboardService dashboardService;

    @MockBean
    private MemberAccessService accessService;
    @MockBean
    private AcademyAccessService academyAccessService;

    @Autowired
    private DashboardRepository dashboardRepository;

    @Test
    @DisplayName("대시보드 생성에 성공한다.")
    void createDashboard_ByDashboardPostParam_ReturnDashboardResult() {
        // Given
        final DashboardPostParam param = DashboardFixture.makePostParam();
        final Long memberId = 1L;

        // When
        DashboardResult result = dashboardService.createDashboard(param, memberId);

        // Then
        assertThat(result).satisfies(value -> {
            assertThat(value.dashboardId()).isNotNegative();
            assertThat(value.scheduleInfos().schedules()).isNotEmpty();
        });
    }

}