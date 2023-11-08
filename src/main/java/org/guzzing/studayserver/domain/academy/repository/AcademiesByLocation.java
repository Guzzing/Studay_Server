package org.guzzing.studayserver.domain.academy.repository;

public record AcademiesByLocation(
        Long academyId,
        String academyName,
        String fullAddress,
        String phoneNumber,
        String areaOfExpertise,
        Double latitude,
        Double longitude
) {

}
