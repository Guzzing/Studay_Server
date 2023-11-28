package org.guzzing.studayserver.domain.academy.facade.dto;

import org.guzzing.studayserver.domain.academy.service.dto.result.AcademiesByLocationResult;
import org.guzzing.studayserver.domain.academy.service.dto.result.AcademiesByLocationResults;
import org.guzzing.studayserver.domain.region.service.dto.location.RegionResult;

import java.util.List;

public record AcademiesByLocationFacadeResult(
        List<AcademyByLocationFacadeResult> academiesByLocationResults,
        String sido,
        String sigungu,
        String upmyeondong
) {

    public static AcademiesByLocationFacadeResult from(
            AcademiesByLocationResults academiesByLocationResults,
            RegionResult regionResult) {
        return new AcademiesByLocationFacadeResult(
                academiesByLocationResults.academiesByLocationResults().stream()
                        .map(academyByLocationResult -> AcademyByLocationFacadeResult.from(academyByLocationResult))
                        .toList(),
                regionResult.sido(),
                regionResult.sigungu(),
                regionResult.upmyeondong()
        );
    }

    public record AcademyByLocationFacadeResult(
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
        public static AcademyByLocationFacadeResult from(AcademiesByLocationResult result) {
            return new AcademyByLocationFacadeResult(
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
