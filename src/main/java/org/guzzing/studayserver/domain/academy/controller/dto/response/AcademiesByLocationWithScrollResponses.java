package org.guzzing.studayserver.domain.academy.controller.dto.response;

import java.util.List;

import org.guzzing.studayserver.domain.academy.service.dto.result.AcademiesByLocationWithScrollResults;

public record AcademiesByLocationWithScrollResponses(
    List<AcademyByLocationWithScrollResponse> academiesByLocationResponse,
    String sido,
    String sigungu,
    String upmyeondong,
    boolean hasNext
) {

    public static AcademiesByLocationWithScrollResponses from(
        AcademiesByLocationWithScrollResults academiesByLocationResult) {
        return new AcademiesByLocationWithScrollResponses(
            academiesByLocationResult.academiesByLocationResults()
                .stream()
                .map(AcademyByLocationWithScrollResponse::from)
                .toList(),
            "서울시",
            "강남구",
            "대치동",
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
            AcademiesByLocationWithScrollResults.AcademiesByLocationResultWithScroll result) {
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
