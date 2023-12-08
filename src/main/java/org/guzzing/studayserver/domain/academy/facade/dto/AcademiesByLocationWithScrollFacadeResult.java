package org.guzzing.studayserver.domain.academy.facade.dto;

import org.guzzing.studayserver.domain.academy.service.dto.result.AcademiesByLocationWithScrollResults;
import org.guzzing.studayserver.domain.region.service.dto.location.RegionResult;
import java.util.List;

public record AcademiesByLocationWithScrollFacadeResult(
        List<AcademyByLocationWithScrollFacadeResult> academiesByLocationResults,
        String sido,
        String sigungu,
        String upmyeondong,
        Long beforeLastId,
        boolean hasNext
) {

    public static AcademiesByLocationWithScrollFacadeResult from(
            AcademiesByLocationWithScrollResults academiesByLocationResults,
            RegionResult regionResult) {
        return new AcademiesByLocationWithScrollFacadeResult(
                academiesByLocationResults
                        .academiesByLocationResults()
                        .stream()
                        .map(academyByLocationResult ->
                                AcademyByLocationWithScrollFacadeResult.from(academyByLocationResult))
                        .toList(),
                regionResult.sido(),
                regionResult.sigungu(),
                regionResult.upmyeondong(),
                academiesByLocationResults.beforeLastId(),
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



