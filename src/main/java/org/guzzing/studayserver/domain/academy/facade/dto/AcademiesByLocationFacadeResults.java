package org.guzzing.studayserver.domain.academy.facade.dto;

import org.guzzing.studayserver.domain.academy.service.dto.result.AcademyByLocationWithCursorAndNotLikeResults;

import java.util.List;
import java.util.Map;

public record AcademiesByLocationFacadeResults(
    List<AcademiesByLocationFacadeResult> academiesByLocationFacadeResults,
    long lastAcademyId,
    boolean hasNext
) {
    public static AcademiesByLocationFacadeResults to(
        AcademyByLocationWithCursorAndNotLikeResults results,
        Map<Long, Boolean> isLikes
    ) {
        return new AcademiesByLocationFacadeResults(
            results.academiesByLocationResults()
                .stream()
                .map(result -> AcademiesByLocationFacadeResult.to(result, isLikes.get(result.academyId())))
                .toList(),
            results.lastAcademyId(),
            results.hasNext()
        );
    }

    public record AcademiesByLocationFacadeResult(
        long academyId,
        String academyName,
        String address,
        String contact,
        List<String> categories,
        double latitude,
        double longitude,
        String shuttleAvailable,
        boolean isLiked
    ) {
        public static AcademiesByLocationFacadeResult to(
            AcademyByLocationWithCursorAndNotLikeResults.AcademiesByLocationWithCursorAndNotLikeResult result,
            boolean isLiked
        ) {
            return new AcademiesByLocationFacadeResult(
                result.academyId(),
                result.academyName(),
                result.address(),
                result.contact(),
                result.categories(),
                result.latitude(),
                result.longitude(),
                result.shuttleAvailable(),
                isLiked
            );
        }
    }
}
