package org.guzzing.studayserver.domain.academy.service.dto.param;

public record AcademiesByLocationParam(
        Double baseLatitude,
        Double baseLongitude,
        Long memberId
) {

    public static AcademiesByLocationParam of(
            Double baseLatitude,
            Double baseLongitude,
            Long memberId) {
        return new AcademiesByLocationParam(baseLatitude, baseLongitude, memberId);
    }

}
