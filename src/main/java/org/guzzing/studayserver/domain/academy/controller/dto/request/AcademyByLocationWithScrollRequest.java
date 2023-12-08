package org.guzzing.studayserver.domain.academy.controller.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import org.guzzing.studayserver.domain.academy.facade.dto.AcademiesByLocationWithScrollFacadeParam;

public record AcademyByLocationWithScrollRequest(
        @NotNull(message = "Latitude cannot be null")
        @DecimalMin(value = "-90", message = "Invalid latitude")
        Double lat,

        @NotNull(message = "Longitude cannot be null")
        @DecimalMin(value = "-180", message = "Invalid longitude")
        Double lng,

        @PositiveOrZero
        Long beforeLastId
) {

    public static AcademiesByLocationWithScrollFacadeParam to(AcademyByLocationWithScrollRequest request, Long memberId) {
        return new AcademiesByLocationWithScrollFacadeParam(
                request.lat,
                request.lng,
                memberId,
                request.beforeLastId
        );
    }
}
