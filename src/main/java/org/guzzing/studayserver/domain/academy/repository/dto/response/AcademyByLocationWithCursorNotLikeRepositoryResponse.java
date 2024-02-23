package org.guzzing.studayserver.domain.academy.repository.dto.response;

import org.guzzing.studayserver.domain.academy.repository.dto.AcademyWithNotLikeByLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public record AcademyByLocationWithCursorNotLikeRepositoryResponse(
    Map<AcademyWithNotLikeByLocation, List<Long>> academiesByLocation,
    Long lastAcademyId,
    boolean hasNext
) {

    public static AcademyByLocationWithCursorNotLikeRepositoryResponse of(
        List<AcademyByLocationWithNotLikeRepositoryResponse> academiesByLocation,
        Long lastAcademyId,
        boolean hasNext
    ) {
        Map<AcademyWithNotLikeByLocation, List<Long>> academyIdWithCategories = new ConcurrentHashMap<>();

        academiesByLocation.forEach(academyByLocation -> academyIdWithCategories.computeIfAbsent(
                AcademyWithNotLikeByLocation.of(academyByLocation),
                k -> new ArrayList<>())
            .add(academyByLocation.getCategoryId()));

        return new AcademyByLocationWithCursorNotLikeRepositoryResponse(
            academyIdWithCategories,
            lastAcademyId,
            hasNext
        );
    }

}
