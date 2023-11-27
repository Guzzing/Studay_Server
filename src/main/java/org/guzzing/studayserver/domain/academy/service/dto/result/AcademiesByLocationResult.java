package org.guzzing.studayserver.domain.academy.service.dto.result;

import org.guzzing.studayserver.domain.academy.repository.dto.AcademiesByLocation;
import org.guzzing.studayserver.domain.academy.util.dto.DistinctFilteredAcademy;

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

    public static AcademiesByLocationResult from(DistinctFilteredAcademy distinctFilteredAcademy, List<String> categories) {
        return new AcademiesByLocationResult(
                distinctFilteredAcademy.academyId(),
                distinctFilteredAcademy.academyName(),
                distinctFilteredAcademy.fullAddress(),
                distinctFilteredAcademy.phoneNumber(),
                categories,
                distinctFilteredAcademy.latitude(),
                distinctFilteredAcademy.longitude(),
                distinctFilteredAcademy.shuttleAvailable(),
                distinctFilteredAcademy.isLiked()
        );
    }

}
