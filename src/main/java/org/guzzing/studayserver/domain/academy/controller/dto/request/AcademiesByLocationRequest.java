package org.guzzing.studayserver.domain.academy.controller.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import org.guzzing.studayserver.domain.academy.service.dto.param.AcademiesByLocationParam;

public record AcademiesByLocationRequest(
        @NotNull(message = "Latitude cannot be null")
        @DecimalMin(value = "-90", message = "Invalid latitude")
        Double lat,

        @NotNull(message = "Longitude cannot be null")
        @DecimalMin(value = "-180", message = "Invalid longitude")
        Double lng
) {

    public static AcademiesByLocationParam to(AcademiesByLocationRequest request) {
        return new AcademiesByLocationParam(
                request.lat,
                request.lng
        );
    }
}
