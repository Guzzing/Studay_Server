package org.guzzing.studayserver.domain.academy.controller.dto.request;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.util.List;
import org.guzzing.studayserver.domain.academy.controller.dto.validation.ValidAreaOfExpertise;
import org.guzzing.studayserver.domain.academy.service.dto.param.AcademyFilterParam;

public record AcademyFilterRequest(

        @NotNull(message = "Latitude cannot be null")
        @DecimalMin(value = "33.0", message = "Invalid latitude")
        @DecimalMax(value = "38.0", message = "Invalid latitude")
        Double lat,

        @NotNull(message = "Longitude cannot be null")
        @DecimalMin(value = "125.0", message = "Invalid longitude")
        @DecimalMax(value = "130.0", message = "Invalid longitude")
        Double lng,

        @ValidAreaOfExpertise
        List<String> areaOfExpertises,

        @PositiveOrZero
        Long desiredMinAmount,

        @PositiveOrZero
        Long desiredMaxAmount
) {

    @AssertTrue(message = "최소 희망 금액이 최대 희망 금액보다 클 수 없습니다.")
    private boolean isValidDesiredAmount() {
        return desiredMaxAmount >= desiredMinAmount;
    }

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
