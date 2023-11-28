package org.guzzing.studayserver.domain.academy.service.dto.result;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.guzzing.studayserver.domain.academy.util.CategoryInfo;
import org.guzzing.studayserver.domain.academy.util.dto.DistinctFilteredAcademy;

public record AcademiesByLocationResults(
        List<AcademiesByLocationResult> academiesByLocationResults
) {

    public static AcademiesByLocationResults to(Map<Long, List<Long>> academyIdWithCategories, Set<DistinctFilteredAcademy> distinctFilteredAcademies) {
        return new AcademiesByLocationResults(
                distinctFilteredAcademies.stream()
                        .map(
                                distinctFilteredAcademy -> AcademiesByLocationResult.from(
                                        distinctFilteredAcademy,
                                        academyIdWithCategories.get(distinctFilteredAcademy.academyId())
                                                .stream()
                                                .map(categoryId -> CategoryInfo.getCategoryNameById(categoryId))
                                                .toList()
                                )
                        )
                        .toList());
    }

}
