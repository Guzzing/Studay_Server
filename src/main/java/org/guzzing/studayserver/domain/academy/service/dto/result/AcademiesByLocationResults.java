package org.guzzing.studayserver.domain.academy.service.dto.result;

import java.util.List;
import java.util.Map;

import org.guzzing.studayserver.domain.academy.repository.dto.AcademiesByLocation;
import org.guzzing.studayserver.domain.academy.util.CategoryInfo;

public record AcademiesByLocationResults(
        List<AcademiesByLocationResult> academiesByLocationResults
) {

    public static AcademiesByLocationResults to(List<AcademiesByLocation> academiesByLocations, Map<Long, List<Long>> academiesWithCategoryIds) {
        return new AcademiesByLocationResults(
                academiesByLocations.stream()
                        .map(academiesByLocation ->
                                AcademiesByLocationResult.from(
                                        academiesByLocation,
                                        academiesWithCategoryIds.get(academiesByLocation.academyId())
                                                .stream()
                                                .map(categoryId -> CategoryInfo.getCategoryNameById(categoryId))
                                                .toList()
                                )
                        )
                        .toList());
    }

}
