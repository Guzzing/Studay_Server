package org.guzzing.studayserver.domain.academy.controller.dto.response;

import org.guzzing.studayserver.domain.academy.service.dto.result.AcademiesByLocationResults;

import java.util.List;

public record AcademiesByLocationResponses(
        List<AcademiesByLocationResponse> academyGetResponses
) {
    public static AcademiesByLocationResponses from(AcademiesByLocationResults academiesByLocationResults) {
        return new AcademiesByLocationResponses (
                academiesByLocationResults.academiesByLocationResults()
                .stream()
                .map(academiesByLocationResult -> AcademiesByLocationResponse.from(academiesByLocationResult))
                .toList()
        );
    }

}
