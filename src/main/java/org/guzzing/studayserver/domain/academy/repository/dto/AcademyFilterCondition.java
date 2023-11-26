package org.guzzing.studayserver.domain.academy.repository.dto;

import java.util.List;

public record AcademyFilterCondition(
        String pointFormat,
        String categories,
        Long desiredMinAmount,
        Long desiredMaxAmount
) {

}
