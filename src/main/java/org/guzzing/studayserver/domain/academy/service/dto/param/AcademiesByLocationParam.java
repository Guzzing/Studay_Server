package org.guzzing.studayserver.domain.academy.service.dto.param;

public record AcademiesByLocationParam(
        Double baseLatitude,
        Double baseLongitude,
        int pageNumber
) {
    public static AcademiesByLocationParam of( Double baseLatitude,
                                               Double baseLongitude,
                                               int pageNumber) {
        return new AcademiesByLocationParam(baseLatitude, baseLongitude, pageNumber);
    }
}
