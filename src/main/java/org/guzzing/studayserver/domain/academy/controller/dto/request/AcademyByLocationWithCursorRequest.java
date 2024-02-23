package org.guzzing.studayserver.domain.academy.controller.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import org.guzzing.studayserver.domain.academy.facade.dto.AcademiesByLocationFacadeParam;
import org.guzzing.studayserver.domain.academy.service.dto.param.AcademyByLocationWithCursorParam;
import org.guzzing.studayserver.domain.academy.util.Latitude;
import org.guzzing.studayserver.domain.academy.util.Longitude;

public record AcademyByLocationWithCursorRequest(
    @NotNull(message = "Latitude cannot be null")
    @DecimalMin(value = "-90", message = "Invalid latitude")
    Double lat,

    @NotNull(message = "Longitude cannot be null")
    @DecimalMin(value = "-180", message = "Invalid longitude")
    Double lng,

    @PositiveOrZero(message = "학원 아이디는 음수일 수 없습니다.")
    Long lastAcademyId
) {

    public AcademyByLocationWithCursorParam toAcademyByLocationWithCursorParam(Long memberId) {
        return new AcademyByLocationWithCursorParam(
            Latitude.of(lat),
            Longitude.of(lng),
            memberId,
            lastAcademyId
        );
    }

    public AcademiesByLocationFacadeParam toAcademiesByLocationFacadeParam(Long memberId) {
        return new AcademiesByLocationFacadeParam(
            Latitude.of(lat),
            Longitude.of(lng),
            memberId,
            lastAcademyId
        );
    }
}
