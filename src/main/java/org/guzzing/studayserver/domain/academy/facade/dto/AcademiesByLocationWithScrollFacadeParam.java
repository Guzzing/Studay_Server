package org.guzzing.studayserver.domain.academy.facade.dto;

import org.guzzing.studayserver.domain.academy.service.dto.param.AcademiesByLocationWithScrollParam;

public record AcademiesByLocationWithScrollFacadeParam(
        Double lat,
        Double lng,
        Long memberId,
        Long beforeLastId

) {

    public static AcademiesByLocationWithScrollParam to(AcademiesByLocationWithScrollFacadeParam param) {
        return new AcademiesByLocationWithScrollParam(
                param.lat,
                param.lng,
                param.memberId,
                param.beforeLastId
        );
    }
}

