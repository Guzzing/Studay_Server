package org.guzzing.studayserver.domain.academy.service.dto.result;

import org.guzzing.studayserver.domain.academy.repository.AcademyByFiltering;

public record AcademyFilterResult(
        Long academyId,
        String academyName,
        String fullAddress,
        String contact,
        String areaOfExpertise,
        Double latitude,
        Double longitude,
        String shuttleAvailable
) {

    public static AcademyFilterResult from(AcademyByFiltering academyByFiltering) {
        return new AcademyFilterResult(
                academyByFiltering.academyId(),
                academyByFiltering.academyName(),
                academyByFiltering.fullAddress(),
                academyByFiltering.phoneNumber(),
                academyByFiltering.areaOfExpertise(),
                academyByFiltering.latitude(),
                academyByFiltering.longitude(),
                academyByFiltering.shuttleAvailable()
        );
    }

}
