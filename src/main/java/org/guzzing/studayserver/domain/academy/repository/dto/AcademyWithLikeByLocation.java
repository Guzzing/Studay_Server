package org.guzzing.studayserver.domain.academy.repository.dto;

import org.guzzing.studayserver.domain.academy.repository.dto.response.AcademyByLocationWithLikeRepositoryResponse;

public record AcademyWithLikeByLocation(
    long academyId,
    String academyName,
    String fullAddress,
    String phoneNumber,
    double latitude,
    double longitude,
    String shuttleAvailable,
    boolean isLiked
) {
    public static AcademyWithLikeByLocation of(AcademyByLocationWithLikeRepositoryResponse response) {
        return new AcademyWithLikeByLocation(
            response.getAcademyId(),
            response.getAcademyName(),
            response.getFullAddress(),
            response.getPhoneNumber(),
            response.getLatitude(),
            response.getLongitude(),
            response.getShuttleAvailable(),
            response.isLiked()
        );
    }
}

