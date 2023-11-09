package org.guzzing.studayserver.domain.academy.service.dto.result;

import java.util.List;

import org.guzzing.studayserver.domain.academy.repository.AcademiesByLocation;

public record AcademiesByLocationResults(
        List<AcademiesByLocationResult> academiesByLocationResults
) {
    public static AcademiesByLocationResults to(List<AcademiesByLocation> academiesByLocations) {
        return new AcademiesByLocationResults(
                academiesByLocations.stream()
                        .map(AcademiesByLocationResult::from)
                        .toList());
    }

}
