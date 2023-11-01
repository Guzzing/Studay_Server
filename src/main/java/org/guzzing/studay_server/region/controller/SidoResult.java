package org.guzzing.studay_server.region.controller;

import java.util.List;

public record SidoResult(
        String nation,
        List<String> sidos,
        int sidoCount
) {

    public static SidoResult from(final List<String> baseRegionSido) {
        return new SidoResult("전국", baseRegionSido, baseRegionSido.size());
    }
}
