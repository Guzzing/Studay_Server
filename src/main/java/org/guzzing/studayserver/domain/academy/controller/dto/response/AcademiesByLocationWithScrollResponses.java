package org.guzzing.studayserver.domain.academy.controller.dto.response;

import org.guzzing.studayserver.domain.academy.facade.dto.AcademiesByLocationWithScrollFacadeResult;

import java.util.List;

public record AcademiesByLocationWithScrollResponses (
        List<AcademyByLocationWithScrollResponse> academiesByLocationResponse,
        String sido,
        String sigungu,
        String upmyeondong,
        Long beforeLastId,
        boolean hasNext
) {

    public static AcademiesByLocationWithScrollResponses from(AcademiesByLocationWithScrollFacadeResult academiesByLocationResult) {
        return new AcademiesByLocationWithScrollResponses(
                academiesByLocationResult.academiesByLocationResults()
                        .stream()
                        .map(AcademyByLocationWithScrollResponse::from)
                        .toList(),
                academiesByLocationResult.sido(),
                academiesByLocationResult.sigungu(),
                academiesByLocationResult.upmyeondong(),
                academiesByLocationResult.beforeLastId(),
                academiesByLocationResult.hasNext()
        );
    }

    public record AcademyByLocationWithScrollResponse(
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

        public static AcademyByLocationWithScrollResponse from(
                AcademiesByLocationWithScrollFacadeResult.AcademyByLocationWithScrollFacadeResult result) {
            return new AcademyByLocationWithScrollResponse(
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
