package org.guzzing.studayserver.domain.academy.controller.dto.response;

import java.util.List;

import org.guzzing.studayserver.domain.academy.facade.dto.AcademiesByLocationFacadeResult;
import org.guzzing.studayserver.domain.academy.service.dto.result.AcademiesByLocationResult;
import org.guzzing.studayserver.domain.academy.service.dto.result.AcademiesByLocationResults;

public record AcademiesByLocationResponses(
        List<AcademiesByLocationResponse> academiesByLocationResponse,
        String sido,
        String sigungu,
        String upmyeondong
) {

    public static AcademiesByLocationResponses from(AcademiesByLocationFacadeResult academiesByLocationResult) {
        return new AcademiesByLocationResponses(
                academiesByLocationResult.academiesByLocationResults()
                        .stream()
                        .map(academiesByLocationFacadeResult -> AcademiesByLocationResponse.from(academiesByLocationFacadeResult))
                        .toList(),
                academiesByLocationResult.sido(),
                academiesByLocationResult.sigungu(),
                academiesByLocationResult.upmyeondong()
        );
    }

    public record AcademiesByLocationResponse(
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

        public static AcademiesByLocationResponse from(
                AcademiesByLocationFacadeResult.AcademyByLocationFacadeResult result) {
            return new AcademiesByLocationResponse(
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
