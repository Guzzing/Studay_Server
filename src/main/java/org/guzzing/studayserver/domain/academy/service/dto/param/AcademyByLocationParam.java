package org.guzzing.studayserver.domain.academy.service.dto.param;

import org.guzzing.studayserver.domain.academy.repository.dto.AcademyByLocationWithCursorRepositoryRequest;
import org.guzzing.studayserver.domain.academy.util.Latitude;
import org.guzzing.studayserver.domain.academy.util.Longitude;
import org.guzzing.studayserver.domain.academy.util.SqlFormatter;

public record AcademyByLocationParam(
    Latitude northEastLatitude,
    Longitude northEastLongitude,
    Latitude southWestbaseLatitude,
    Longitude southWestLongitude,
    Long memberId,
    Long lastAcademyId
) {
    public AcademyByLocationWithCursorRepositoryRequest toAcademyByLocationWithCursorRequest(
    ) {
        return new AcademyByLocationWithCursorRepositoryRequest(
            SqlFormatter.makeDiagonalByLineString(
                northEastLatitude,
                northEastLongitude,
                southWestbaseLatitude,
                southWestLongitude
            ),
            memberId,
            lastAcademyId
        );
    }
}