package org.guzzing.studayserver.domain.dashboard.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

import org.guzzing.studayserver.domain.academy.service.AcademyAccessService;
import org.guzzing.studayserver.domain.child.service.ChildAccessService;
import org.guzzing.studayserver.domain.dashboard.fixture.DashboardFixture;
import org.guzzing.studayserver.domain.dashboard.model.Dashboard;
import org.guzzing.studayserver.domain.dashboard.service.dto.request.DashboardPostParam;
import org.guzzing.studayserver.domain.dashboard.service.dto.response.DashboardGetResult;
import org.guzzing.studayserver.domain.dashboard.service.dto.response.DashboardGetResults;
import org.guzzing.studayserver.domain.dashboard.service.dto.response.DashboardPostResult;
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

    @Autowired
    private DashboardFixture dashboardFixture;

    @MockBean
    private MemberAccessService memberAccessService;
    @MockBean
    private AcademyAccessService academyAccessService;
    @MockBean
    private ChildAccessService childAccessService;

    @Test
    @DisplayName("대시보드 생성에 성공한다.")
    void createDashboard_ByDashboardPostParam_ReturnDashboardResult() {
        // Given
        final long memberId = 1L;
        final DashboardPostParam param = dashboardFixture.makePostParam();

        // When
        final DashboardPostResult result = dashboardService.createDashboard(param, memberId);

        // Then
        assertThat(result).satisfies(value -> {
            assertThat(value.dashboardId()).isNotNegative();
            assertThat(value.scheduleInfos().schedules()).isNotEmpty();
        });
    }

    @Test
    @DisplayName("아이디로 대시보드를 조회한다.")
    void findDashboard_ByDashboardId() {
        // Given
        given(childAccessService.findChildInfo(anyLong())).willReturn(dashboardFixture.makeChildInfo());
        given(academyAccessService.findAcademyInfo(anyLong())).willReturn(dashboardFixture.makeAcademyInfo());
        given(academyAccessService.findLessonInfo(anyLong())).willReturn(dashboardFixture.makeLessonInfo());

        final long memberId = 1L;
        final Dashboard dashboard = dashboardFixture.createActiveEntity();

        // When
        final DashboardGetResult getResult = dashboardService.findDashboard(dashboard.getId(), memberId);

        // Then
        assertThat(getResult).satisfies(result -> {
            assertThat(result.dashboardId()).isEqualTo(dashboard.getId());
            assertThat(result.childInfo().childId()).isEqualTo(dashboard.getChildId());
            assertThat(result.lessonInfo().lessonId()).isEqualTo(dashboard.getLessonId());
        });
    }

    @Test
    @DisplayName("아이의 대시보드 중 활성화 된 것만 조회한다.")
    void findDashboards_ActiveOnly_OfChild() {
        // Given
        given(childAccessService.findChildInfo(anyLong())).willReturn(dashboardFixture.makeChildInfo());
        given(academyAccessService.findAcademyInfo(anyLong())).willReturn(dashboardFixture.makeAcademyInfo());
        given(academyAccessService.findLessonInfo(anyLong())).willReturn(dashboardFixture.makeLessonInfo());

        final boolean activeOnly = true;

        final Dashboard activeDashboard = dashboardFixture.createActiveEntity();
        final Dashboard inActiveDashboard = dashboardFixture.createNotActiveEntity();

        // When
        final DashboardGetResults getResults = dashboardService.findDashboards(
                activeDashboard.getChildId(), activeOnly, 1L);

        // Then
        assertThat(getResults.results()).satisfies(results -> {
            assertThat(results).hasSize(1);
            assertThat(results.get(0).active()).isTrue();
            assertThat(results.get(0).childInfo().childId()).isEqualTo(activeDashboard.getChildId());
        });
    }

    @Test
    @DisplayName("아이의 대시보드 모두를 조회한다.")
    void findDashboards_All_OfChild() {
        // Given
        given(childAccessService.findChildInfo(anyLong())).willReturn(dashboardFixture.makeChildInfo());
        given(academyAccessService.findAcademyInfo(anyLong())).willReturn(dashboardFixture.makeAcademyInfo());
        given(academyAccessService.findLessonInfo(anyLong())).willReturn(dashboardFixture.makeLessonInfo());

        final long memberId = 1L;
        final boolean activeOnly = false;

        final Dashboard activeDashboard = dashboardFixture.createActiveEntity();
        final Dashboard inActiveDashboard = dashboardFixture.createNotActiveEntity();

        // When
        final DashboardGetResults results = dashboardService.findDashboards(
                activeDashboard.getChildId(), activeOnly, memberId);

        // Then
        assertThat(results.results()).satisfies(result -> {
            assertThat(result.get(0).childInfo().childId()).isEqualTo(activeDashboard.getChildId());
            assertThat(result.get(0).childInfo().childId()).isEqualTo(inActiveDashboard.getChildId());
            assertThat(result.get(0).active()).isTrue();
            assertThat(result.get(1).active()).isFalse();
        });
    }

}