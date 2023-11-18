package org.guzzing.studayserver.domain.academy.service.dto.result;

import org.guzzing.studayserver.domain.academy.repository.dto.AcademiesByName;

public record AcademiesByNameResult(
        Long academyId,
        String academyName,
        String address,
        Double latitude,
        Double longitude
) {

    public static AcademiesByNameResult from(AcademiesByName academiesByName) {
        return new AcademiesByNameResult(
                academiesByName.getAcademyId(),
                academiesByName.getAcademyName(),
                academiesByName.getFullAddress(),
                academiesByName.getLatitude(),
                academiesByName.getLongitude()
        );
    }

}
