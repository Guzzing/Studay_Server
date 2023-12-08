package org.guzzing.studayserver.domain.academy.service.dto.param;

public record AcademiesByLocationWithScrollParam (
        Double baseLatitude,
        Double baseLongitude,
        Long memberId,
        Long beforeLastId
) {

    public static AcademiesByLocationWithScrollParam of(
            Double baseLatitude,
            Double baseLongitude,
            Long memberId,
            Long beforeLastId) {
        return new AcademiesByLocationWithScrollParam(
                baseLatitude,
                baseLongitude,
                memberId,
                beforeLastId);
    }
}

