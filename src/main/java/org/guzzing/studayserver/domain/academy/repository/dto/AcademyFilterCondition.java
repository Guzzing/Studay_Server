package org.guzzing.studayserver.domain.academy.repository.dto;

public record AcademyFilterCondition(
        String pointFormat,
        String areaOfExpertises,
        Long desiredMinAmount,
        Long desiredMaxAmount
) {

}
