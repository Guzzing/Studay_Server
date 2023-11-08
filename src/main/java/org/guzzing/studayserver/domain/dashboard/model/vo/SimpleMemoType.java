package org.guzzing.studayserver.domain.dashboard.model.vo;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
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

    public static List<SimpleMemoType> getSelectedSimpleMemos(final Map<SimpleMemoType, Boolean> map) {
        return map.entrySet()
                .stream()
                .filter(Entry::getValue)
                .map(Entry::getKey)
                .toList();
    }

    public static Map<SimpleMemoType, Boolean> convertToSimpleMemoMap(final List<SimpleMemoType> list) {
        return Arrays.stream(SimpleMemoType.values())
                .collect(Collectors.toMap(
                        simpleMemoType -> simpleMemoType,
                        list::contains));
    }

}
