package org.guzzing.studayserver.domain.academy.controller.dto.response;

import org.guzzing.studayserver.domain.academy.service.dto.result.AcademiesByLocationResult;

public record AcademiesByLocationResponse(
        Long academyId,
        String academyName,
        String address,
        String contact,
        String areaOfExpertise,
        Double latitude,
        Double longitude
) {
    public static AcademiesByLocationResponse from(AcademiesByLocationResult academiesByLocationResult) {
        return new AcademiesByLocationResponse(
                academiesByLocationResult.academyId(),
                academiesByLocationResult.academyName(),
                academiesByLocationResult.address(),
                academiesByLocationResult.contact(),
                academiesByLocationResult.areaOfExpertise(),
                academiesByLocationResult.latitude(),
                academiesByLocationResult.longitude()
        );
    }
}
