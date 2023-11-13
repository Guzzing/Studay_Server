package org.guzzing.studayserver.domain.dashboard.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

import org.guzzing.studayserver.domain.academy.service.AcademyAccessService;
import org.guzzing.studayserver.domain.child.service.ChildAccessService;
import org.guzzing.studayserver.domain.dashboard.fixture.DashboardFixture;
import org.guzzing.studayserver.domain.dashboard.repository.DashboardRepository;
import org.guzzing.studayserver.domain.dashboard.service.dto.request.DashboardPostParam;
import org.guzzing.studayserver.domain.dashboard.service.dto.response.DashboardGetResult;
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

    @MockBean
    private MemberAccessService accessService;
    @MockBean
    private AcademyAccessService academyAccessService;
    @MockBean
    private ChildAccessService childAccessService;

    @Autowired
    private DashboardRepository dashboardRepository;

    @Test
    @DisplayName("대시보드 생성에 성공한다.")
    void createDashboard_ByDashboardPostParam_ReturnDashboardResult() {
        // Given & Wnen
        DashboardPostResult result = createDashboard(1L);

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
        given(childAccessService.findChildInfo(anyLong())).willReturn(DashboardFixture.makeChildInfo());
        given(academyAccessService.findACademyInfo(anyLong())).willReturn(DashboardFixture.makeAcademyInfo());
        given(academyAccessService.findLessonInfo(anyLong())).willReturn(DashboardFixture.makeLessonInfo());

        final long memberId = 1L;
        final DashboardPostResult postResult = createDashboard(memberId);

        // When
        final DashboardGetResult getResult = dashboardService.findDashboard(postResult.dashboardId(), memberId);

        // Then
        assertThat(getResult).satisfies(result -> {
            assertThat(result.dashboardId()).isEqualTo(postResult.dashboardId());
            assertThat(result.childInfo().childId()).isEqualTo(postResult.childId());
            assertThat(result.lessonInfo().lessonId()).isEqualTo(postResult.lessonId());
        });
    }

    private DashboardPostResult createDashboard(final Long memberId) {
        final DashboardPostParam param = DashboardFixture.makePostParam();

        return dashboardService.createDashboard(param, memberId);
    }

}