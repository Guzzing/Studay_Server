package org.guzzing.studayserver.domain.academy.controller.dto.response;

import org.guzzing.studayserver.domain.academy.service.dto.result.AcademiesByNameResults;
import org.springframework.data.domain.Slice;


public record AcademiesByNameResponses(
        Slice<AcademiesByNameResponse> academiesByNameResponses
) {

    public static AcademiesByNameResponses from(AcademiesByNameResults academiesByNameResults) {
        return new AcademiesByNameResponses(
                academiesByNameResults.academiesByNameResults()
                        .map(academiesByNameResult -> AcademiesByNameResponse.from(academiesByNameResult))
        );
    }
}
