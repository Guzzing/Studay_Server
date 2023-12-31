package org.guzzing.studayserver.domain.region.service.dto.beopjungdong;

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
