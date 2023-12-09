package org.guzzing.studayserver.domain.academy.service.dto.param;

public record AcademiesByLocationWithScrollParam(
        Double baseLatitude,
        Double baseLongitude,
        Long memberId,
        int pageNumber
) {

    public static AcademiesByLocationWithScrollParam of(
            Double baseLatitude,
            Double baseLongitude,
            Long memberId,
            int pageNumber) {
        return new AcademiesByLocationWithScrollParam(
                baseLatitude,
                baseLongitude,
                memberId,
                pageNumber);
    }
}
