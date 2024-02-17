package org.guzzing.studayserver.domain.academy.controller.dto.response;

import org.guzzing.studayserver.domain.academy.service.dto.result.AcademiesByLocationWithScrollResults;
import org.guzzing.studayserver.domain.academy.service.dto.result.AcademyByLocationWithCursorResults;

import java.util.List;

public record AcademyByLocationWithCursorResponses(
    List<AcademyByLocationWithCursorResponse> academiesByLocationResults,
    Long lastAcademyId,
    boolean hasNext
) {
    public static AcademyByLocationWithCursorResponses from(AcademyByLocationWithCursorResults results) {
        return new AcademyByLocationWithCursorResponses(
            results.academiesByLocationResults()
                .stream()
                .map(AcademyByLocationWithCursorResponse::from)
                .toList(),
            results.lastAcademyId(),
            results.hasNext()
        );
    }

    public record AcademyByLocationWithCursorResponse(
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

        public static AcademyByLocationWithCursorResponse from(
            AcademyByLocationWithCursorResults.AcademiesByLocationWithCursorResult result) {
            return new AcademyByLocationWithCursorResponse(
                result.academyId(),
                result.academyName(),
                result.address(),
                result.contact(),
                result.categories(),
                result.latitude(),
                result.longitude(),
                result.shuttleAvailable(),
                result.isLiked()
            );
        }
    }
}

