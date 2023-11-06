package org.guzzing.studayserver.domain.academy.service.dto.result;

import org.guzzing.studayserver.domain.academy.repository.AcademiesByLocation;

import java.util.List;

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
