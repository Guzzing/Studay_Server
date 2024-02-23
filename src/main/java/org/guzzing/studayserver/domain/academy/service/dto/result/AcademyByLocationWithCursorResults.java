package org.guzzing.studayserver.domain.academy.service.dto.result;

import org.guzzing.studayserver.domain.academy.repository.dto.AcademyWithLikeByLocation;
import org.guzzing.studayserver.domain.academy.repository.dto.response.AcademyByLocationWithCursorRepositoryResponse;
import org.guzzing.studayserver.domain.academy.util.CategoryInfo;

import java.util.List;

public record AcademyByLocationWithCursorResults(
    List<AcademiesByLocationWithCursorResult> academiesByLocationResults,
    Long lastAcademyId,
    boolean hasNext
) {

    public static AcademyByLocationWithCursorResults to(
        AcademyByLocationWithCursorRepositoryResponse academyByLocationWithCursorRepositoryResponse) {
        return new AcademyByLocationWithCursorResults(
            academyByLocationWithCursorRepositoryResponse
                .academiesByLocation()
                .keySet()
                .stream()
                .map(academyByLocation ->
                    AcademiesByLocationWithCursorResult.from(
                        academyByLocation,
                        academyByLocationWithCursorRepositoryResponse.academiesByLocation().
                            get(academyByLocation)))
                .toList(),
            academyByLocationWithCursorRepositoryResponse.lastAcademyId(),
            academyByLocationWithCursorRepositoryResponse.hasNext());
    }

    public record AcademiesByLocationWithCursorResult(
        Long academyId,
        String academyName,
        String address,
        String contact,
        List<String> categories,
        Double latitude,
        Double longitude,
        String shuttleAvailable,
        boolean isLiked
    ) {

        public static AcademiesByLocationWithCursorResult from(
            AcademyWithLikeByLocation academyByLocation,
            List<Long> categories) {
            return new AcademiesByLocationWithCursorResult(
                academyByLocation.academyId(),
                academyByLocation.academyName(),
                academyByLocation.fullAddress(),
                academyByLocation.phoneNumber(),
                categories.stream()
                    .map(CategoryInfo::getCategoryNameById)
                    .toList(),
                academyByLocation.latitude(),
                academyByLocation.longitude(),
                academyByLocation.shuttleAvailable(),
                academyByLocation.isLiked()
            );
        }

    }
}