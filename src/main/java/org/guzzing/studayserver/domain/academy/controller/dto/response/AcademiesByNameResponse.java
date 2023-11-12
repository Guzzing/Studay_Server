package org.guzzing.studayserver.domain.academy.controller.dto.response;

import org.guzzing.studayserver.domain.academy.service.dto.result.AcademiesByNameResult;

public record AcademiesByNameResponse(
        Long academyId,
        String academyName,
        String address,
        Double latitude,
        Double longitude
) {

    public static AcademiesByNameResponse from(AcademiesByNameResult academiesByNameResult) {
        return new AcademiesByNameResponse(
                academiesByNameResult.academyId(),
                academiesByNameResult.academyName(),
                academiesByNameResult.address(),
                academiesByNameResult.latitude(),
                academiesByNameResult.longitude()
        );
    }
}
