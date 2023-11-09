package org.guzzing.studayserver.domain.academy.controller.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.List;
import org.guzzing.studayserver.domain.academy.controller.dto.validation.ValidAreaOfExpertise;
import org.guzzing.studayserver.domain.academy.service.dto.param.AcademyFilterParam;

public record AcademyFilterRequest(
        @NotNull(message = "Latitude cannot be null")
        @DecimalMin(value = "-90", message = "Invalid latitude")
        Double lat,

        @NotNull(message = "Longitude cannot be null")
        @DecimalMin(value = "-180", message = "Invalid longitude")
        Double lng,

        @ValidAreaOfExpertise
        List<String> areaOfExpertises,

        @Positive
        Long desiredMinAmount,

        @Positive
        Long desiredMaxAmount
) {

    public static AcademyFilterParam to(AcademyFilterRequest request) {
        return new AcademyFilterParam(
                request.lat,
                request.lng,
                request.areaOfExpertises(),
                request.desiredMinAmount,
                request.desiredMaxAmount
        );
    }
}
