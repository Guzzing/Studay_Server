package org.guzzing.studayserver.domain.academy.controller.dto.request;

import org.guzzing.studayserver.domain.academy.service.dto.param.AcademiesByNameParam;

public record AcademiesByNameRequest(
        String academyName,
        int pageNumber
) {

    public static AcademiesByNameParam to(AcademiesByNameRequest request) {
        return new AcademiesByNameParam(
                request.academyName,
                request.pageNumber
        );
    }

}
