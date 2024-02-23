package org.guzzing.studayserver.domain.academy.service.dto.param;

import org.guzzing.studayserver.domain.academy.repository.dto.request.AcademyByLocationWithCursorRepositoryRequest;
import org.guzzing.studayserver.domain.academy.util.Latitude;
import org.guzzing.studayserver.domain.academy.util.Longitude;

public record AcademyByLocationWithCursorParam(
    Latitude baseLatitude,
    Longitude baseLongitude,
    Long memberId,
    Long lastAcademyId
) {
    public AcademyByLocationWithCursorRepositoryRequest toAcademyByLocationWithCursorRequest(
        String diagonal
    ) {
        return new AcademyByLocationWithCursorRepositoryRequest(
            diagonal,
            memberId,
            lastAcademyId
        );
    }
}