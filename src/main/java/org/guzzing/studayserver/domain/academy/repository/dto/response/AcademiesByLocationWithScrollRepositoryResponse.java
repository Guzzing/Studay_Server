package org.guzzing.studayserver.domain.academy.repository.dto.response;

import org.guzzing.studayserver.domain.academy.repository.dto.AcademyWithLikeByLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public record AcademiesByLocationWithScrollRepositoryResponse(
    Map<AcademyWithLikeByLocation, List<Long>> academiesByLocation,
    boolean hasNext
) {

    public static AcademiesByLocationWithScrollRepositoryResponse of(
        List<AcademyByLocationWithLikeRepositoryResponse> academiesByLocation,
        boolean hasNext
    ) {
        Map<AcademyWithLikeByLocation, List<Long>> academyIdWithCategories = new ConcurrentHashMap<>();

        academiesByLocation.forEach(academyByLocationWithScroll -> academyIdWithCategories.computeIfAbsent(
                AcademyWithLikeByLocation.of(academyByLocationWithScroll),
                k -> new ArrayList<>())
            .add(academyByLocationWithScroll.getCategoryId()));

        return new AcademiesByLocationWithScrollRepositoryResponse(
            academyIdWithCategories,
            hasNext
        );
    }


}
