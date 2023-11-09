package org.guzzing.studayserver.domain.academy.repository;

public record AcademyByFiltering(
        Long academyId,
        String academyName,
        String fullAddress,
        String phoneNumber,
        String areaOfExpertise,
        Double latitude,
        Double longitude,
        String shuttleAvailable,
        boolean isLiked
) {
}
