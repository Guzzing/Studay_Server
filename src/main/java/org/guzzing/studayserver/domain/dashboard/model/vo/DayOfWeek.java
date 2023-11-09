package org.guzzing.studayserver.domain.dashboard.model.vo;

import java.util.Arrays;
import java.util.Objects;
import org.guzzing.studayserver.global.exception.DashboardException;

public enum DayOfWeek {
    NONE,
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
                .orElse(NONE);
    }
}
