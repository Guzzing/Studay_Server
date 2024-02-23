package org.guzzing.studayserver.domain.academy.service.dto.result;

import org.guzzing.studayserver.domain.academy.repository.dto.AcademyWithNotLikeByLocation;
import org.guzzing.studayserver.domain.academy.repository.dto.response.AcademyByLocationWithCursorNotLikeRepositoryResponse;
import org.guzzing.studayserver.domain.academy.util.CategoryInfo;

import java.util.List;

public record AcademyByLocationWithCursorAndNotLikeResults (
    List<AcademiesByLocationWithCursorAndNotLikeResult> academiesByLocationResults,
    Long lastAcademyId,
    boolean hasNext
) {

    public static AcademyByLocationWithCursorAndNotLikeResults to(
        AcademyByLocationWithCursorNotLikeRepositoryResponse response) {
        return new AcademyByLocationWithCursorAndNotLikeResults(
            response
                .academiesByLocation()
                .keySet()
                .stream()
                .map(academyByLocation ->
                    AcademiesByLocationWithCursorAndNotLikeResult.from(
                        academyByLocation,
                        response.academiesByLocation().
                            get(academyByLocation)))
                .toList(),
            response.lastAcademyId(),
            response.hasNext());
    }

    public record AcademiesByLocationWithCursorAndNotLikeResult(
        Long academyId,
        String academyName,
        String address,
        String contact,
        List<String> categories,
        Double latitude,
        Double longitude,
        String shuttleAvailable
    ) {

        public static AcademiesByLocationWithCursorAndNotLikeResult from(
            AcademyWithNotLikeByLocation academyByLocation,
            List<Long> categories) {
            return new AcademiesByLocationWithCursorAndNotLikeResult(
                academyByLocation.academyId(),
                academyByLocation.academyName(),
                academyByLocation.fullAddress(),
                academyByLocation.phoneNumber(),
                categories.stream()
                    .map(CategoryInfo::getCategoryNameById)
                    .toList(),
                academyByLocation.latitude(),
                academyByLocation.longitude(),
                academyByLocation.shuttleAvailable()
            );
        }

    }
}
