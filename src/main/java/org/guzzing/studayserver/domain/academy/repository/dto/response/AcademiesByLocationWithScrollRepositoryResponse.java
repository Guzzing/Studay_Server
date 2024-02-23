package org.guzzing.studayserver.domain.academy.repository.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public record AcademiesByLocationWithScroll(
        Map<AcademyByLocation, List<Long>> academiesByLocation,
        boolean hasNext
) {

    public static AcademiesByLocationWithScroll of(
            List<AcademyByLocationWithScroll> academiesByLocation,
            boolean hasNext
    ) {
        Map<AcademyByLocation, List<Long>> academyIdWithCategories = new ConcurrentHashMap<>();

        academiesByLocation.forEach(academyByLocationWithScroll -> academyIdWithCategories.computeIfAbsent(
                        AcademyByLocation.of(academyByLocationWithScroll),
                        k -> new ArrayList<>())
                .add(academyByLocationWithScroll.categoryId()));

        return new AcademiesByLocationWithScroll(
                academyIdWithCategories,
                hasNext
        );
    }


}
