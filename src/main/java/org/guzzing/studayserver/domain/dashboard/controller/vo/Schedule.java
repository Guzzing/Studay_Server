package org.guzzing.studayserver.domain.dashboard.controller.vo;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
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
        final LocalTime start = parseTime(startTime);
        final LocalTime end = parseTime(endTime);

        if (start.isAfter(end)) {
            throw new DashboardException("시작 시간이 종료 시간 보다 늦습니다.");
        }
    }

    private LocalTime parseTime(final String time) {
        final List<Integer> timeData = Arrays.stream(time.split(":"))
                .map(Integer::parseInt)
                .toList();

        return LocalTime.of(timeData.get(0), timeData.get(1));
    }

}
