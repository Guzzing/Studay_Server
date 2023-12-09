package org.guzzing.studayserver.domain.academy.facade.dto;

import java.util.List;
import org.guzzing.studayserver.domain.academy.service.dto.result.AcademiesByLocationWithScrollResults;
import org.guzzing.studayserver.domain.region.service.dto.location.RegionResult;

public record AcademiesByLocationWithScrollFacadeResult(
        List<AcademyByLocationWithScrollFacadeResult> academiesByLocationResults,
        String sido,
        String sigungu,
        String upmyeondong,
        boolean hasNext
) {

    public static AcademiesByLocationWithScrollFacadeResult from(
            AcademiesByLocationWithScrollResults academiesByLocationResults,
            RegionResult regionResult) {
        return new AcademiesByLocationWithScrollFacadeResult(
                academiesByLocationResults
                        .academiesByLocationResults()
                        .stream()
                        .map(AcademyByLocationWithScrollFacadeResult::from)
                        .toList(),
                regionResult.sido(),
                regionResult.sigungu(),
                regionResult.upmyeondong(),
                academiesByLocationResults.hasNext()
        );
    }

    public record AcademyByLocationWithScrollFacadeResult(
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

        public static AcademyByLocationWithScrollFacadeResult from(
                AcademiesByLocationWithScrollResults.AcademiesByLocationResultWithScroll result) {
            return new AcademyByLocationWithScrollFacadeResult(
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



