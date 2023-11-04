package org.guzzing.studayserver.domain.academy.service.dto.param;

public record AcademiesByNameParam(
        String academyName,
        int pageNumber
) {
}
