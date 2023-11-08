package org.guzzing.studayserver.domain.academy.service.dto.result;

import org.guzzing.studayserver.domain.academy.repository.AcademyByFiltering;

import java.util.List;

public record AcademyFilterResults (
        List<AcademyFilterResult> academyFilterResults
) {
    public static AcademyFilterResults from(List<AcademyByFiltering> academiesByFiltering) {
        return new AcademyFilterResults(
                academiesByFiltering.stream()
                        .map(academyByFiltering -> AcademyFilterResult.from(academyByFiltering))
                        .toList()
        );
    }
}
