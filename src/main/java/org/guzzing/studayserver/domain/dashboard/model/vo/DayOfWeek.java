package org.guzzing.studayserver.domain.dashboard.model.vo;

import java.util.Arrays;
import java.util.Objects;
import org.guzzing.studayserver.global.exception.DashboardException;

public enum DayOfWeek {
    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THURSDAY,
    FRIDAY,
    SATURDAY,
    SUNDAY;

    public static DayOfWeek of(final String value) {
        return Arrays.stream(DayOfWeek.values())
                .filter(dayOfWeek -> Objects.equals(dayOfWeek.name(), value))
                .findAny()
                .orElseThrow(() -> new DashboardException("유효하지 않은 요일입니다."));
    }
}
