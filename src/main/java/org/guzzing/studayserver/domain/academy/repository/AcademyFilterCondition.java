package org.guzzing.studayserver.domain.academy.repository;

public record AcademyFilterCondition(
        String pointFormat,
        String areaOfExpertises,
        Long desiredMinAmount,
        Long desiredMaxAmount
) {
}
