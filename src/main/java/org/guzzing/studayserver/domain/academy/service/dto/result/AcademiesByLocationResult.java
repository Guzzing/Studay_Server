package org.guzzing.studayserver.domain.academy.service.dto.result;

import org.guzzing.studayserver.domain.academy.repository.dto.AcademiesByLocation;

import java.util.List;

public record AcademiesByLocationResult(
        Long academyId,
        String academyName,
        String address,
        String contact,
        List<String> categories,
        Double latitude,
        Double longitude,
        String shuttleAvailable,
        boolean isLiked
) {

    public static AcademiesByLocationResult from(AcademiesByLocation academiesByLocation, List<String> categories) {
        return new AcademiesByLocationResult(
                academiesByLocation.academyId(),
                academiesByLocation.academyName(),
                academiesByLocation.fullAddress(),
                academiesByLocation.phoneNumber(),
                categories,
                academiesByLocation.latitude(),
                academiesByLocation.longitude(),
                academiesByLocation.shuttleAvailable(),
                academiesByLocation.isLiked()
        );
    }

}
