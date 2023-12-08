package org.guzzing.studayserver.domain.academy.repository.dto;

public record AcademyByFilterWithScroll(
        Long academyId,
        String academyName,
        String fullAddress,
        String phoneNumber,
        Double latitude,
        Double longitude,
        String shuttleAvailable,
        boolean isLiked
) {

}
