package org.guzzing.studayserver.domain.academy.controller.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import org.guzzing.studayserver.domain.academy.service.dto.param.AcademiesByLocationWithScrollParam;
import org.guzzing.studayserver.domain.academy.util.Latitude;
import org.guzzing.studayserver.domain.academy.util.Longitude;

public record AcademyByLocationWithScrollRequest(
    @NotNull(message = "Latitude cannot be null")
    @DecimalMin(value = "-90", message = "Invalid latitude")
    Double lat,

    @NotNull(message = "Longitude cannot be null")
    @DecimalMin(value = "-180", message = "Invalid longitude")
    Double lng,

    @PositiveOrZero
    int pageNumber
) {

    public AcademiesByLocationWithScrollParam to(Long memberId) {
        return new AcademiesByLocationWithScrollParam(
            Latitude.of(lat),
            Longitude.of(lng),
            memberId,
            pageNumber
        );
    }
}
