package org.guzzing.studayserver.domain.academy.repository.dto;

public record AcademyFilterCondition(
        String pointFormat,
        String categories,
        Long desiredMinAmount,
        Long desiredMaxAmount
) {

}
