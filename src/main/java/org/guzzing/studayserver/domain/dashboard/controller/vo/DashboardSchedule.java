package org.guzzing.studayserver.domain.dashboard.controller.vo;

import java.time.LocalDateTime;
import org.guzzing.studayserver.global.exception.DashboardException;

public record DashboardSchedule(
        String dayOfWeek,
        LocalDateTime startTime,
        LocalDateTime endTime
) {

    public DashboardSchedule {
        validateTimeSuitability(startTime, endTime);
    }

    private void validateTimeSuitability(
            final LocalDateTime startTime,
            final LocalDateTime endTime
    ) {
        if (startTime.isAfter(endTime)) {
            throw new DashboardException("시작 시간이 종료 시간 보다 늦습니다.");
        }
    }

}
