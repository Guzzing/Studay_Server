package org.guzzing.studayserver.domain.academy.controller.dto.response;

import java.util.List;

import org.guzzing.studayserver.domain.academy.service.dto.result.AcademyFilterResults;

public record AcademyFilterResponses(
        List<AcademyFilterResponse> academyFilterResponses
) {

    public static AcademyFilterResponses from(AcademyFilterResults academyFilterResults) {
        return new AcademyFilterResponses(
                academyFilterResults.academyFilterResults()
                        .stream()
                        .map(academyFilterResult -> AcademyFilterResponse.from(academyFilterResult))
                        .toList()
        );
    }
}

