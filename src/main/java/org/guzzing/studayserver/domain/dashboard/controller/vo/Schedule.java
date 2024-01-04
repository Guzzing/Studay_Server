package org.guzzing.studayserver.domain.dashboard.controller.vo;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalTime;
import org.guzzing.studayserver.global.common.time.TimeConverter;
import org.guzzing.studayserver.global.exception.DashboardException;

public record Schedule(
        @Positive Integer dayOfWeek,
        @NotNull String startTime,
        @NotNull String endTime
) {

    public Schedule {
        validateTimeSuitability(startTime, endTime);
    }

    private void validateTimeSuitability(
            final String startTime,
            final String endTime
    ) {
        final LocalTime start = TimeConverter.getLocalTime(startTime);
        final LocalTime end = TimeConverter.getLocalTime(endTime);

        if (start.isAfter(end)) {
            throw new DashboardException("시작 시간이 종료 시간 보다 늦습니다.");
        }
    }

}
