package org.guzzing.studayserver.domain.academy.facade.dto;

import org.guzzing.studayserver.domain.academy.service.dto.param.AcademiesByLocationParam;

public record AcademiesByLocationFacadeParam(
        Double lat,
        Double lng,
        Long memberId
) {

    public static AcademiesByLocationParam to(AcademiesByLocationFacadeParam param) {
        return new AcademiesByLocationParam(
                param.lat,
                param.lng,
                param.memberId
        );
    }
}
