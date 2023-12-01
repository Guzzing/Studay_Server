package org.guzzing.studayserver.domain.region.model.vo;

import java.util.List;

public enum RegionUnit {
    SIDO(List.of("시", "도")),
    SIGUNGU(List.of("시", "군", "구")),
    UPMYEONDONG(List.of("구", "읍", "면", "동"));

    private final List<String> unitValue;

    RegionUnit(List<String> unitValue) {
        this.unitValue = unitValue;
    }

    public boolean isMatched(final String input) {
        return this.unitValue.stream()
                .anyMatch(input::contains);
    }
}
