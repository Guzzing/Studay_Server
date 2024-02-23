package org.guzzing.studayserver.domain.academy.repository.dto;

import org.guzzing.studayserver.domain.academy.repository.dto.response.AcademyByLocationWithLikeRepositoryResponse;
import org.guzzing.studayserver.domain.academy.repository.dto.response.AcademyByLocationWithNotLikeRepositoryResponse;

public record AcademyWithNotLikeByLocation (
    long academyId,
    String academyName,
    String fullAddress,
    String phoneNumber,
    double latitude,
    double longitude,
    String shuttleAvailable
) {
    public static AcademyWithNotLikeByLocation of(AcademyByLocationWithNotLikeRepositoryResponse response) {
        return new AcademyWithNotLikeByLocation(
            response.getAcademyId(),
            response.getAcademyName(),
            response.getFullAddress(),
            response.getPhoneNumber(),
            response.getLatitude(),
            response.getLongitude(),
            response.getShuttleAvailable()
        );
    }
}
