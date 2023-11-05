package org.guzzing.studayserver.domain.academy.service.dto.param;

public record AcademiesByLocationParam(
        Double baseLatitude,
        Double baseLongitude
) {
    public static AcademiesByLocationParam of(Double baseLatitude,
                                              Double baseLongitude) {
        return new AcademiesByLocationParam(baseLatitude, baseLongitude);
    }
}
