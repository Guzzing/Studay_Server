package org.guzzing.studayserver.domain.academy.facade.dto;

import org.guzzing.studayserver.domain.academy.service.dto.param.AcademyByLocationWithCursorParam;
import org.guzzing.studayserver.domain.academy.util.Latitude;
import org.guzzing.studayserver.domain.academy.util.Longitude;

public record AcademiesByLocationFacadeParam(
    Latitude baseLatitude,
    Longitude baseLongitude,
    Long memberId,
    Long lastAcademyId
) {

    public AcademyByLocationWithCursorParam toAcademyByLocationWithCursorParam() {
        return new AcademyByLocationWithCursorParam(
            baseLatitude,
            baseLongitude,
            memberId,
            lastAcademyId
        );
    }
}
