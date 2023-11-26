package org.guzzing.studayserver.domain.academy.util.dto;

public record DistinctFilteredAcademy(
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
