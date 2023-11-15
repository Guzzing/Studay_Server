package org.guzzing.studayserver.domain.dashboard.model;

import static java.time.DayOfWeek.FRIDAY;
import static java.time.DayOfWeek.MONDAY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.guzzing.studayserver.domain.dashboard.model.vo.Repeatance.BIWEEKLY;
import static org.guzzing.studayserver.domain.dashboard.model.vo.Repeatance.WEEKLY;
import static org.guzzing.studayserver.domain.dashboard.model.vo.SimpleMemoType.CHEAP_FEE;
import static org.guzzing.studayserver.domain.dashboard.model.vo.SimpleMemoType.LOVELY_TEACHING;

import java.time.LocalDate;
import java.util.List;
import org.guzzing.studayserver.domain.dashboard.model.vo.FeeInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DashboardTest {

    @Test
    @DisplayName("대시보드 객체 생성 성공한다.")
    void createDashboard_ByValidParams() {
        // Given & When
        final Dashboard result = new Dashboard(
                1L, 1L, 1L,
                List.of(new DashboardSchedule(MONDAY, "14:00", "18:00", WEEKLY),
                        new DashboardSchedule(FRIDAY, "13:30", "15:40", BIWEEKLY)),
                new FeeInfo(1_000L, 1_000L, 1_000L, 1_000L, LocalDate.of(LocalDate.now().getYear(), 4, 23)),
                List.of(CHEAP_FEE, LOVELY_TEACHING),
                true, true
        );

        // Then
        assertThat(result.getDashboardSchedules()).isNotEmpty();
    }

}