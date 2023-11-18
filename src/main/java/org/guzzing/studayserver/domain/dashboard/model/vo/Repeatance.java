package org.guzzing.studayserver.domain.dashboard.model.vo;

import java.util.Arrays;
import java.util.Objects;
import org.guzzing.studayserver.global.exception.DashboardException;

public enum Repeatance {
    NONE,
    DAILY,
    WEEKLY,
    BIWEEKLY,
    MONTHLY,
    YEARLY;

    public static Repeatance of(final String value) {
        return Arrays.stream(Repeatance.values())
                .filter(repeatance -> Objects.equals(repeatance.name(), value))
                .findAny()
                .orElseThrow(() -> new DashboardException("설정한 반복 주기와 일치하는 주기가 없습니다."));
    }

}
