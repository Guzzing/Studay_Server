package org.guzzing.studayserver.domain.dashboard.facade;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

import java.util.List;
import org.guzzing.studayserver.domain.academy.service.AcademyAccessService;
import org.guzzing.studayserver.domain.child.service.ChildAccessService;
import org.guzzing.studayserver.domain.dashboard.facade.dto.DashboardGetResult;
import org.guzzing.studayserver.domain.dashboard.facade.dto.DashboardGetResults;
import org.guzzing.studayserver.domain.dashboard.facade.dto.DashboardPatchResult;
import org.guzzing.studayserver.domain.dashboard.facade.dto.DashboardPostResult;
import org.guzzing.studayserver.domain.dashboard.facade.dto.DashboardPutResult;
import org.guzzing.studayserver.domain.dashboard.fixture.DashboardFixture;
import org.guzzing.studayserver.domain.dashboard.model.Dashboard;
import org.guzzing.studayserver.domain.dashboard.service.dto.request.DashboardPostParam;
import org.guzzing.studayserver.domain.dashboard.service.dto.request.DashboardPutParam;
import org.guzzing.studayserver.domain.member.service.MemberAccessService;
import org.guzzing.studayserver.global.exception.DashboardException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class DashboardFacadeTest {

    @Autowired
    private DashboardFacade dashboardFacade;
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
    void createDashboard_ByDashboardParam_ReturnDashboardResult() {
        // Given
        final long memberId = 1L;
        final DashboardPostParam param = dashboardFixture.makePostParam();

        // When
        final DashboardPostResult result = dashboardFacade.createDashboard(param, memberId);

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
        given(childAccessService.findChildInfo(anyLong())).willReturn(dashboardFixture.makeChildInfo());
        given(academyAccessService.findAcademyInfo(anyLong())).willReturn(dashboardFixture.makeAcademyInfo());
        given(academyAccessService.findLessonInfo(anyLong())).willReturn(dashboardFixture.makeLessonInfo());

        final long memberId = 1L;

        final Dashboard activeDashboard = dashboardFixture.createActiveEntity();

        // When & Then
        assertThatThrownBy(() -> dashboardFacade.removeDashboard(activeDashboard.getId(), memberId))
                .isInstanceOf(DashboardException.class)
                .hasMessage("비활성화된 대시보드만 삭제가 가능합니다.");
    }

    @Test
    @DisplayName("바활성화된 대시보드는 제거할 수 있다.")
    void deleteDashboard_InActiveDashboard_Deletable() {
        // Given
        given(childAccessService.findChildInfo(anyLong())).willReturn(dashboardFixture.makeChildInfo());
        given(academyAccessService.findAcademyInfo(anyLong())).willReturn(dashboardFixture.makeAcademyInfo());
        given(academyAccessService.findLessonInfo(anyLong())).willReturn(dashboardFixture.makeLessonInfo());

        final long memberId = 1L;

        final Dashboard inActiveDashboard = dashboardFixture.createNotActiveEntity();

        // When
        dashboardFacade.removeDashboard(inActiveDashboard.getId(), memberId);

        // Then
        final List<Dashboard> results = dashboardFixture.findAll();

        assertThat(results).isNotEmpty();
        assertThat(results.get(0).isDeleted()).isTrue();
    }

    @Test
    @DisplayName("대시보드 활성화 여부를 반전한다.")
    void toggleActive_DashboardActiveBoolean_Revert() {
        // Given
        final long memberId = 1L;
        final Dashboard activeEntity = dashboardFixture.createActiveEntity();

        // When
        final DashboardPatchResult result = dashboardFacade.revertDashboardActiveness(activeEntity.getId(), memberId);

        // Then
        assertThat(result.isActive()).isFalse();
    }

    @Test
    @DisplayName("대시보드를 수정한다.")
    void editDashboard_DashboardParam_UpdateDashboard() {
        // Given
        given(childAccessService.findChildInfo(anyLong())).willReturn(dashboardFixture.makeChildInfo());
        given(academyAccessService.findAcademyInfo(anyLong())).willReturn(dashboardFixture.makeAcademyInfo());
        given(academyAccessService.findLessonInfo(anyLong())).willReturn(dashboardFixture.makeLessonInfo());

        final long memberId = 1L;
        final Dashboard dashboard = dashboardFixture.createActiveEntity();

        final DashboardPutParam param = dashboardFixture.makePutParam(dashboard.getId());

        // When
        final DashboardPutResult dashboardPostResult = dashboardFacade.modifyDashboard(param, memberId);

        // Then
        assertThat(dashboardPostResult).satisfies(result -> {
            assertThat(result.dashboardId()).isEqualTo(dashboard.getId());
            assertThat(result.paymentInfo()).isEqualTo(param.paymentInfo());
            assertThat(result.simpleMemo()).isNotNull();
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
        final DashboardGetResult getResult = dashboardFacade.getDashboard(dashboard.getId(), memberId);

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
        final DashboardGetResults getResults = dashboardFacade.getDashboards(
                activeDashboard.getChildId(), activeOnly, 1L);

        // Then
        assertThat(getResults.results()).satisfies(results -> {
            assertThat(results).hasSize(1);
            assertThat(results.get(0).isActive()).isTrue();
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
        final DashboardGetResults results = dashboardFacade.getDashboards(
                activeDashboard.getChildId(), activeOnly, memberId);

        // Then
        assertThat(results.results()).satisfies(result -> {
            assertThat(result.get(0).childInfo().childId()).isEqualTo(activeDashboard.getChildId());
            assertThat(result.get(0).childInfo().childId()).isEqualTo(inActiveDashboard.getChildId());
            assertThat(result.get(0).isActive()).isTrue();
            assertThat(result.get(1).isActive()).isFalse();
        });
    }

}