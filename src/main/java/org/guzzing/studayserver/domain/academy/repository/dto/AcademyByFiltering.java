package org.guzzing.studayserver.domain.academy.repository.dto;

public record AcademyByFiltering(
        Long academyId,
        String academyName,
        String fullAddress,
        String phoneNumber,
        Long categoryId,
        Double latitude,
        Double longitude,
        String shuttleAvailable,
        boolean isLiked
) {

}
