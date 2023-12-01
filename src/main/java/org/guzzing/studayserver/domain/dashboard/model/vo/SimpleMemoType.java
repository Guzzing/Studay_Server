package org.guzzing.studayserver.domain.dashboard.model.vo;

import lombok.Getter;

@Getter
public enum SimpleMemoType {
    KINDNESS("kindness"),
    GOOD_FACILITY("goodFacility"),
    CHEAP_FEE("cheapFee"),
    GOOD_MANAGEMENT("goodManagement"),
    LOVELY_TEACHING("lovelyTeaching"),
    SHUTTLE_AVAILABILITY("shuttleAvailability");

    private final String type;

    SimpleMemoType(final String type) {
        this.type = type;
    }

}
