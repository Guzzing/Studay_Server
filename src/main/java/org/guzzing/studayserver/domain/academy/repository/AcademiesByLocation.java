package org.guzzing.studayserver.domain.academy.repository;

public record AcademiesByLocation(
        Long academyId,
        String academyName,
        String address,
        String contact,
        String areaOfExpertise
) {
}
