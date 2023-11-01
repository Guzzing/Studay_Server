package org.guzzing.studay_server.region.service;

import java.util.List;

public record SigunguResult(
        String sido,
        List<String> sigungus,
        int sigunguCount
) {

    public static SigunguResult from(final String sido, final List<String> sigungus) {
        return new SigunguResult(sido, sigungus, sigungus.size());
    }

}
