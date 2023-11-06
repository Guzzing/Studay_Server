package org.guzzing.studayserver.domain.academy.service.dto.result;

import org.guzzing.studayserver.domain.academy.repository.AcademiesByName;
import org.springframework.data.domain.Slice;

public record AcademiesByNameResults(
        Slice<AcademiesByNameResult> academiesByNameResults
) {

    public static AcademiesByNameResults to(Slice<AcademiesByName> academiesByNames) {
        return new AcademiesByNameResults(
                academiesByNames.map(AcademiesByNameResult::from));
    }
}
