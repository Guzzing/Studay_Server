package org.guzzing.studayserver.domain.academy.model.vo.academyinfo;

import java.util.Objects;

public enum ShuttleAvailability {
    AVAILABLE,
    NEED_INQUIRE,
    FREE;

    public static ShuttleAvailability getShuttleAvailability(final String shuttle) {
        return Objects.equals(shuttle, "0") ? NEED_INQUIRE : AVAILABLE;
    }
}
