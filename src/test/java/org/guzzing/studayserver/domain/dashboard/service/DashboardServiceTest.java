package org.guzzing.studayserver.domain.dashboard.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.guzzing.studayserver.domain.academy.service.AcademyAccessService;
import org.guzzing.studayserver.domain.dashboard.fixture.DashboardFixture;
import org.guzzing.studayserver.domain.dashboard.repository.DashboardRepository;
import org.guzzing.studayserver.domain.dashboard.service.dto.request.DashboardPostParam;
import org.guzzing.studayserver.domain.dashboard.service.dto.response.DashboardResult;
import org.guzzing.studayserver.domain.dashboard.service.dto.response.DashboardResults;
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
        // Given & Wnen
        DashboardResult result = createDashboard(1L);

        // Then
        assertThat(result).satisfies(value -> {
            assertThat(value.dashboardId()).isNotNegative();
            assertThat(value.scheduleInfos().schedules()).isNotEmpty();
        });
    }

    @Test
    @DisplayName("활성화된 대시보드만 조회한다.")
    void findDashboardOfChild_OnlyActiveDashboard() {
        // Given
        final Long childId = 1L;
        final Boolean activeOnly = true;
        final Long memberId = 1L;

        createDashboard(memberId);

        // When
        final DashboardResults dashboardResults = dashboardService.findDashboardOfChild(childId, activeOnly, memberId);

        // Then
        final List<DashboardResult> results = dashboardResults.results();

        assertThat(results).isNotEmpty();
        assertThat(results.get(0).active()).isTrue();
    }

    @Test
    @DisplayName("아이의 모든 대시보드를 조회한다.")
    void findDashboardOfChild_AllDashboard() {
        // Given
        final Long childId = 1L;
        final Boolean activeOnly = true;
        final Long memberId = 1L;

        createDashboard(memberId);

        // When
        final DashboardResults dashboardResults = dashboardService.findDashboardOfChild(childId, activeOnly, memberId);

        // Then
        final List<DashboardResult> results = dashboardResults.results();

        assertThat(results).isNotEmpty();
        ;
    }

    private DashboardResult createDashboard(final Long memberId) {
        final DashboardPostParam param = DashboardFixture.makePostParam();

        return dashboardService.createDashboard(param, memberId);
    }

}