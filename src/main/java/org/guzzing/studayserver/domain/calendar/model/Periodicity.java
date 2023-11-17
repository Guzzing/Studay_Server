package org.guzzing.studayserver.domain.calendar.model;

import java.util.Arrays;
import java.util.Objects;

public enum Periodicity {
    DAILY,
    WEEKLY,
    MONTHLY,
    YEARLY;

    public static Periodicity of(final String value) {
        return Arrays.stream(Periodicity.values())
                .filter(periodicity -> Objects.equals(periodicity.name(), value))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("설정한 반복 주기와 일치하는 주기가 없습니다."));
    }
}
