package org.guzzing.studayserver.domain.academy.service.dto.param;

import org.guzzing.studayserver.domain.academy.util.Latitude;
import org.guzzing.studayserver.domain.academy.util.Longitude;

public record AcademiesByLocationWithScrollParam(
    Latitude baseLatitude,
    Longitude baseLongitude,
    Long memberId,
    int pageNumber
) {

    public static AcademiesByLocationWithScrollParam of(
        Latitude baseLatitude,
        Longitude baseLongitude,
        Long memberId,
        int pageNumber) {
        return new AcademiesByLocationWithScrollParam(
            baseLatitude,
            baseLongitude,
            memberId,
            pageNumber);
    }
}
