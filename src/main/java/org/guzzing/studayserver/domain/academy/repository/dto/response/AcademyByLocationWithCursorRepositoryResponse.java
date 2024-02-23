package org.guzzing.studayserver.domain.academy.repository.dto.response;

import org.guzzing.studayserver.domain.academy.repository.dto.AcademyWithLikeByLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public record AcademyByLocationWithCursorRepositoryResponse(
    Map<AcademyWithLikeByLocation, List<Long>> academiesByLocation,
    Long lastAcademyId,
    boolean hasNext
) {

    public static AcademyByLocationWithCursorRepositoryResponse of(
        List<AcademyByLocationWithLikeRepositoryResponse> academiesByLocation,
        Long lastAcademyId,
        boolean hasNext
    ) {
        Map<AcademyWithLikeByLocation, List<Long>> academyIdWithCategories = new ConcurrentHashMap<>();

        academiesByLocation.forEach(academyByLocation -> academyIdWithCategories.computeIfAbsent(
                AcademyWithLikeByLocation.of(academyByLocation),
                k -> new ArrayList<>())
            .add(academyByLocation.getCategoryId()));

        return new AcademyByLocationWithCursorRepositoryResponse(
            academyIdWithCategories,
            lastAcademyId,
            hasNext
        );
    }

}
