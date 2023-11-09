package org.guzzing.studayserver.domain.dashboard.controller.vo;

import java.time.LocalTime;
import org.guzzing.studayserver.global.exception.DashboardException;

public record Schedule(
        String dayOfWeek,
        LocalTime startTime,
        LocalTime endTime,
        String repeatance
) {

    public Schedule {
        validateTimeSuitability(startTime, endTime);
    }

    private void validateTimeSuitability(
            final LocalTime startTime,
            final LocalTime endTime
    ) {
        if (startTime.isAfter(endTime)) {
            throw new DashboardException("시작 시간이 종료 시간 보다 늦습니다.");
        }
    }

}
