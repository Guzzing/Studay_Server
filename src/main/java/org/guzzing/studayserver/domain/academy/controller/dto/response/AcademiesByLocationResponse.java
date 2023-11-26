package org.guzzing.studayserver.domain.academy.controller.dto.response;

import org.guzzing.studayserver.domain.academy.service.dto.result.AcademiesByLocationResult;

public record AcademiesByLocationResponse(
        Long academyId,
        String academyName,
        String address,
        String contact,
        String areaOfExpertise,
        Double latitude,
        Double longitude,
        String shuttleAvailable,
        boolean isLiked
) {

    public static AcademiesByLocationResponse from(AcademiesByLocationResult academiesByLocationResult) {
        return new AcademiesByLocationResponse(
                academiesByLocationResult.academyId(),
                academiesByLocationResult.academyName(),
                academiesByLocationResult.address(),
                academiesByLocationResult.contact(),
                academiesByLocationResult.categories(),
                academiesByLocationResult.latitude(),
                academiesByLocationResult.longitude(),
                academiesByLocationResult.shuttleAvailable(),
                academiesByLocationResult.isLiked()
        );
    }
}
