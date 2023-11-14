package org.guzzing.studayserver.domain.dashboard.service;

import static java.time.DayOfWeek.FRIDAY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.guzzing.studayserver.domain.dashboard.model.vo.Repeatance.WEEKLY;
import static org.guzzing.studayserver.domain.dashboard.model.vo.Repeatance.YEARLY;
import static org.guzzing.studayserver.domain.dashboard.model.vo.SimpleMemoType.CHEAP_FEE;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import org.guzzing.studayserver.domain.academy.service.AcademyAccessService;
import org.guzzing.studayserver.domain.child.service.ChildAccessService;
import org.guzzing.studayserver.domain.dashboard.fixture.DashboardFixture;
import org.guzzing.studayserver.domain.dashboard.model.Dashboard;
import org.guzzing.studayserver.domain.dashboard.model.dto.PaymentInfo;
import org.guzzing.studayserver.domain.dashboard.model.vo.SimpleMemoType;
import org.guzzing.studayserver.domain.dashboard.service.dto.request.DashboardParam;
import org.guzzing.studayserver.domain.dashboard.service.dto.response.DashboardGetResult;
import org.guzzing.studayserver.domain.dashboard.service.dto.response.DashboardGetResults;
import org.guzzing.studayserver.domain.dashboard.service.dto.response.DashboardPatchResult;
import org.guzzing.studayserver.domain.dashboard.service.dto.response.DashboardResult;
import org.guzzing.studayserver.domain.dashboard.service.vo.ScheduleInfo;
import org.guzzing.studayserver.domain.dashboard.service.vo.ScheduleInfos;
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
    void createDashboard_ByDashboardParam_ReturnDashboardResult() {
        // Given
        final long memberId = 1L;
        final DashboardParam param = dashboardFixture.makePostParam();

        // When
        final DashboardResult result = dashboardService.createDashboard(param, memberId);

        // Then
        assertThat(result).satisfies(value -> {
            assertThat(value.dashboardId()).isNotNegative();
            assertThat(value.scheduleInfos().schedules()).isNotEmpty();
        });
    }

    @Test
    @DisplayName("대시보드 수정를 수정한다.")
    void editDashboard_DashboardParam_UpdateDashboard() {
        // Given
        given(childAccessService.findChildInfo(anyLong())).willReturn(dashboardFixture.makeChildInfo());
        given(academyAccessService.findAcademyInfo(anyLong())).willReturn(dashboardFixture.makeAcademyInfo());
        given(academyAccessService.findLessonInfo(anyLong())).willReturn(dashboardFixture.makeLessonInfo());

        final long memberId = 1L;
        final Dashboard dashboard = dashboardFixture.createActiveEntity();

        final DashboardParam param = makePostParam();

        // When
        final DashboardResult dashboardResult = dashboardService.editDashboard(dashboard.getId(), param, memberId);

        // Then
        assertThat(dashboardResult).satisfies(result -> {
            assertThat(result.dashboardId()).isEqualTo(dashboard.getId());
            assertThat(result.academyId()).isEqualTo(param.academyId());
            assertThat(result.lessonId()).isEqualTo(param.lessonId());
            assertThat(result.paymentInfo()).isEqualTo(param.paymentInfo());
            assertThat(result.scheduleInfos()).isEqualTo(param.scheduleInfos());
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
        final DashboardGetResults results = dashboardService.findDashboards(
                activeDashboard.getChildId(), activeOnly, memberId);

        // Then
        assertThat(results.results()).satisfies(result -> {
            assertThat(result.get(0).childInfo().childId()).isEqualTo(activeDashboard.getChildId());
            assertThat(result.get(0).childInfo().childId()).isEqualTo(inActiveDashboard.getChildId());
            assertThat(result.get(0).isActive()).isTrue();
            assertThat(result.get(1).isActive()).isFalse();
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
        assertThatThrownBy(() -> dashboardService.deleteDashboard(activeDashboard.getId(), memberId))
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
        dashboardService.deleteDashboard(inActiveDashboard.getId(), memberId);

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
        final DashboardPatchResult result = dashboardService.toggleActiveOfDashboard(activeEntity.getId(), memberId);

        // Then
        assertThat(result.isActive()).isFalse();
    }

    private DashboardParam makePostParam() {
        return new DashboardParam(
                1L, 1L, 1L,
                new ScheduleInfos(List.of(
                        new ScheduleInfo(FRIDAY, "14:00", "18:00", WEEKLY),
                        new ScheduleInfo(null, "12:30", "12:04", YEARLY))),
                new PaymentInfo(4_000L, 4_000L, 4_000L, 4_000L, LocalDate.now()),
                Map.of(
                        SimpleMemoType.KINDNESS, false,
                        SimpleMemoType.GOOD_FACILITY, true,
                        SimpleMemoType.GOOD_MANAGEMENT, true,
                        CHEAP_FEE, false,
                        SimpleMemoType.LOVELY_TEACHING, true,
                        SimpleMemoType.SHUTTLE_AVAILABILITY, true));
    }

}