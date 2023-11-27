package org.guzzing.studayserver.domain.dashboard.model;

import static java.time.DayOfWeek.FRIDAY;
import static java.time.DayOfWeek.MONDAY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.guzzing.studayserver.domain.dashboard.model.vo.Repeatance.WEEKLY;
import static org.guzzing.studayserver.domain.dashboard.model.vo.SimpleMemoType.CHEAP_FEE;
import static org.guzzing.studayserver.domain.dashboard.model.vo.SimpleMemoType.GOOD_FACILITY;
import static org.guzzing.studayserver.domain.dashboard.model.vo.SimpleMemoType.GOOD_MANAGEMENT;
import static org.guzzing.studayserver.domain.dashboard.model.vo.SimpleMemoType.KINDNESS;
import static org.guzzing.studayserver.domain.dashboard.model.vo.SimpleMemoType.LOVELY_TEACHING;
import static org.guzzing.studayserver.domain.dashboard.model.vo.SimpleMemoType.SHUTTLE_AVAILABILITY;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import org.guzzing.studayserver.domain.dashboard.model.vo.FeeInfo;
import org.guzzing.studayserver.domain.dashboard.model.vo.SimpleMemo;
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
                        new DashboardSchedule(FRIDAY, "13:30", "15:40", WEEKLY)),
                new FeeInfo(1_000L, 1_000L, 1_000L, 1_000L, LocalDate.of(LocalDate.now().getYear(), 4, 23)),
                Map.of(KINDNESS.getType(), false,
                        GOOD_FACILITY.getType(), false,
                        CHEAP_FEE.getType(), true,
                        GOOD_MANAGEMENT.getType(), true,
                        LOVELY_TEACHING.getType(), false,
                        SHUTTLE_AVAILABILITY.getType(), true),
                true, true);

        // Then
        assertThat(result.getDashboardSchedules()).isNotEmpty();
    }

}