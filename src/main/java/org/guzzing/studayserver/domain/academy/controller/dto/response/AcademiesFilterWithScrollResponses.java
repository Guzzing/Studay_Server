package org.guzzing.studayserver.domain.academy.controller.dto.response;

import java.util.List;
import org.guzzing.studayserver.domain.academy.service.dto.result.AcademiesFilterWithScrollResults;

public record AcademiesFilterWithScrollResponses(
        List<AcademyFilterWithScrollResponse> AcademiesFilterWithScrollResponses,
        boolean hasNext
) {

    public static AcademiesFilterWithScrollResponses from(
            AcademiesFilterWithScrollResults academiesFilterWithScrollResults) {
        return new AcademiesFilterWithScrollResponses(
                academiesFilterWithScrollResults.academiesFilterWithScrollResults()
                        .stream()
                        .map(academyFilterWithScrollResult -> AcademyFilterWithScrollResponse.from(
                                academyFilterWithScrollResult))
                        .toList(),
                academiesFilterWithScrollResults.hasNext()
        );
    }

    public record AcademyFilterWithScrollResponse(
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

        public static AcademyFilterWithScrollResponse from(
                AcademiesFilterWithScrollResults.AcademyFilterWithScrollResult academyFilterWithScrollResult) {
            return new AcademyFilterWithScrollResponse(
                    academyFilterWithScrollResult.academyId(),
                    academyFilterWithScrollResult.academyName(),
                    academyFilterWithScrollResult.fullAddress(),
                    academyFilterWithScrollResult.contact(),
                    academyFilterWithScrollResult.categories(),
                    academyFilterWithScrollResult.latitude(),
                    academyFilterWithScrollResult.longitude(),
                    academyFilterWithScrollResult.shuttleAvailable(),
                    academyFilterWithScrollResult.isLiked()
            );
        }
    }
}
