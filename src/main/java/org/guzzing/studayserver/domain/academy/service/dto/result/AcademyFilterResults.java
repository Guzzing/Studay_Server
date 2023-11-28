package org.guzzing.studayserver.domain.academy.service.dto.result;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.guzzing.studayserver.domain.academy.repository.dto.AcademyByFiltering;
import org.guzzing.studayserver.domain.academy.util.FilterParser;
import org.guzzing.studayserver.domain.academy.util.dto.DistinctFilteredAcademy;

public record AcademyFilterResults(
        List<AcademyFilterResult> academyFilterResults
) {

    public static AcademyFilterResults from(
            Map<Long, List<Long>> academyIdWithCategories,
            Set<DistinctFilteredAcademy> distinctFilteredAcademies) {

        return new AcademyFilterResults(
                distinctFilteredAcademies.stream()
                        .map(distinctFilteredAcademy ->
                                AcademyFilterResult.from(
                                        distinctFilteredAcademy,
                                        academyIdWithCategories.get(distinctFilteredAcademy.academyId())))
                        .toList()
        );
    }

}
