package org.guzzing.studayserver.domain.academy.service.dto.result;

import org.guzzing.studayserver.domain.academy.repository.AcademiesByLocation;

public record AcademiesByLocationResult(
        Long academyId,
        String academyName,
        String address,
        String contact,
        String areaOfExpertise
) {

    public static AcademiesByLocationResult from(AcademiesByLocation academiesByLocation) {
        return new AcademiesByLocationResult(
                academiesByLocation.academyId(),
                academiesByLocation.academyName(),
                academiesByLocation.fullAddress(),
                academiesByLocation.contact(),
                academiesByLocation.areaOfExpertise()
        );
    }
}
