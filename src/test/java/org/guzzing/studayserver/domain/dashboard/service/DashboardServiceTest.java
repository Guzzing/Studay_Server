package org.guzzing.studayserver.domain.dashboard.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.guzzing.studayserver.testutil.fixture.dashboard.DashboardFixture;
import org.guzzing.studayserver.domain.dashboard.model.Dashboard;
import org.guzzing.studayserver.domain.dashboard.service.dto.request.DashboardPostParam;
import org.guzzing.studayserver.domain.dashboard.service.dto.request.DashboardPutParam;
import org.guzzing.studayserver.domain.dashboard.service.dto.response.DashboardResult;
import org.guzzing.studayserver.global.exception.DashboardException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class DashboardServiceTest {

    @Autowired
    private DashboardService dashboardService;
    @Autowired
    private DashboardFixture dashboardFixture;

    @Test
    @DisplayName("대시보드 생성에 성공한다.")
    void createDashboard_ByDashboardParam_ReturnDashboardResult() {
        // Given
        final DashboardPostParam param = dashboardFixture.makePostParam();

        // When
        final DashboardResult result = dashboardService.saveDashboard(param);

        // Then
        assertThat(result).satisfies(value -> {
            assertThat(value.dashboardId()).isNotNegative();
            assertThat(value.scheduleInfos().schedules()).isNotEmpty();
        });
    }

    @Test
    @DisplayName("활성화된 대시보드는 제거할 수 없다.")
    void deleteDashboard_ActiveDashboard_NotDeletable() {
        // Given
        final Dashboard activeDashboard = dashboardFixture.createActiveEntity();

        // When & Then
        assertThatThrownBy(() -> dashboardService.deleteDashboard(activeDashboard.getId()))
                .isInstanceOf(DashboardException.class)
                .hasMessage("비활성화된 대시보드만 삭제가 가능합니다.");
    }

    @Test
    @DisplayName("바활성화된 대시보드는 제거할 수 있다.")
    void deleteDashboard_InActiveDashboard_Deletable() {
        // Given
        final Dashboard inActiveDashboard = dashboardFixture.createNotActiveEntity();

        // When
        dashboardService.deleteDashboard(inActiveDashboard.getId());

        // Then
        final List<Dashboard> results = dashboardFixture.findAll();

        assertThat(results).isNotEmpty();
        assertThat(results.get(0).isDeleted()).isTrue();
    }

    @Test
    @DisplayName("대시보드 수정를 수정한다.")
    void editDashboard_DashboardParam_UpdateDashboard() {
        // Given
        final Dashboard dashboard = dashboardFixture.createActiveEntity();
        final DashboardPutParam param = dashboardFixture.makePutParam(dashboard.getId());

        // When
        final DashboardResult dashboardResult = dashboardService.editDashboard(param);

        // Then
        assertThat(dashboardResult).satisfies(result -> {
            assertThat(result.dashboardId()).isEqualTo(dashboard.getId());
            assertThat(result.paymentInfo()).isEqualTo(param.paymentInfo());
            assertThat(result.simpleMemo()).isNotNull();
        });
    }

    @Test
    @DisplayName("대시보드 활성화 여부를 반전한다.")
    void toggleActive_DashboardActiveBoolean_Revert() {
        // Given
        final Dashboard activeEntity = dashboardFixture.createActiveEntity();

        // When
        final DashboardResult result = dashboardService.toggleDashboardActiveness(activeEntity.getId());

        // Then
        assertThat(result.isActive()).isFalse();
    }

    @Test
    @DisplayName("아이디로 대시보드를 조회한다.")
    void findDashboard_ByDashboardId() {
        // Given
        final Dashboard dashboard = dashboardFixture.createActiveEntity();

        // When
        final DashboardResult getResult = dashboardService.findDashboard(dashboard.getId());

        // Then
        assertThat(getResult).satisfies(result -> {
            assertThat(result.dashboardId()).isEqualTo(dashboard.getId());
            assertThat(result.childId()).isEqualTo(dashboard.getChildId());
            assertThat(result.lessonId()).isEqualTo(dashboard.getLessonId());
        });
    }

    @Test
    @DisplayName("아이의 대시보드 중 활성화 된 것만 조회한다.")
    void findDashboards_ActiveOnly_OfChild() {
        // Given
        final boolean activeOnly = true;

        final Dashboard activeDashboard = dashboardFixture.createActiveEntity();
        final Dashboard inActiveDashboard = dashboardFixture.createNotActiveEntity();

        // When
        final List<DashboardResult> results = dashboardService.findDashboards(activeDashboard.getChildId(), activeOnly);

        // Then
        assertThat(results).hasSize(1);
        assertAll(
                () -> assertTrue(results.stream().allMatch(DashboardResult::isActive)),
                () -> assertTrue(results.stream().allMatch(result -> result.childId() == activeDashboard.getChildId()))
        );
    }

    @Test
    @DisplayName("아이의 대시보드 모두를 조회한다.")
    void findDashboards_All_OfChild() {
        // Given
        final boolean activeOnly = false;

        final Dashboard activeDashboard = dashboardFixture.createActiveEntity();
        final Dashboard inActiveDashboard = dashboardFixture.createNotActiveEntity();

        // When
        final List<DashboardResult> results = dashboardService.findDashboards(activeDashboard.getChildId(), activeOnly);

        // Then
        assertThat(results).satisfies(result -> {
            assertThat(result.get(0).childId()).isEqualTo(activeDashboard.getChildId());
            assertThat(result.get(0).childId()).isEqualTo(inActiveDashboard.getChildId());
            assertThat(result.get(0).isActive()).isTrue();
            assertThat(result.get(1).isActive()).isFalse();
        });
    }

}