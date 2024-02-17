package org.guzzing.studayserver.domain.academy.repository.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public record AcademyByLocationWithCursorRepositoryResponse(
    Map<AcademyByLocation, List<Long>> academiesByLocation,
    Long lastAcademyId,
    boolean hasNext
) {

    public static AcademyByLocationWithCursorRepositoryResponse of(
        List<AcademyByLocationWithScroll> academiesByLocation,
        Long lastAcademyId,
        boolean hasNext
    ) {
        Map<AcademyByLocation, List<Long>> academyIdWithCategories = new ConcurrentHashMap<>();

        academiesByLocation.forEach(academyByLocation -> academyIdWithCategories.computeIfAbsent(
                AcademyByLocation.of(academyByLocation),
                k -> new ArrayList<>())
            .add(academyByLocation.categoryId()));

        return new AcademyByLocationWithCursorRepositoryResponse(
            academyIdWithCategories,
            lastAcademyId,
            hasNext
        );
    }

    public record AcademyByLocation(
        Long academyId,
        String academyName,
        String fullAddress,
        String phoneNumber,
        Double latitude,
        Double longitude,
        String shuttleAvailable,
        boolean isLiked
    ) {

        public static AcademyByLocation of(AcademyByLocationWithScroll academyByLocationWithScroll) {
            return new AcademyByLocation(
                academyByLocationWithScroll.academyId(),
                academyByLocationWithScroll.academyName(),
                academyByLocationWithScroll.fullAddress(),
                academyByLocationWithScroll.phoneNumber(),
                academyByLocationWithScroll.latitude(),
                academyByLocationWithScroll.longitude(),
                academyByLocationWithScroll.shuttleAvailable(),
                academyByLocationWithScroll.isLiked()
            );
        }
    }

}
