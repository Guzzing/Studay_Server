package org.guzzing.studayserver.domain.dashboard.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.guzzing.studayserver.domain.dashboard.fixture.DashboardFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DashboardTest {

    @Test
    @DisplayName("대시보드 객체 생성 성공한다.")
    void createDashboard_ByValidParams() {
        // Given & When
        Dashboard result = DashboardFixture.makeEntity();

        // Then
        assertThat(result.getDashboardSchedules()).isNotEmpty();
    }

}