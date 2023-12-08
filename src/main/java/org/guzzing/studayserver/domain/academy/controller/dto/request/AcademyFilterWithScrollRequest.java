package org.guzzing.studayserver.domain.academy.controller.dto.request;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import org.guzzing.studayserver.domain.academy.controller.dto.validation.ValidCategoryName;
import org.guzzing.studayserver.domain.academy.service.dto.param.AcademyFilterWithScrollParam;

public record AcademyFilterWithScrollRequest(
        @NotNull(message = "Latitude cannot be null")
        @DecimalMin(value = "33.0", message = "Invalid latitude")
        @DecimalMax(value = "38.0", message = "Invalid latitude")
        Double lat,

        @NotNull(message = "Longitude cannot be null")
        @DecimalMin(value = "125.0", message = "Invalid longitude")
        @DecimalMax(value = "130.0", message = "Invalid longitude")
        Double lng,

        @ValidCategoryName
        List<String> categories,

        Long desiredMinAmount,

        Long desiredMaxAmount,

        int pageNumber
) {

    @AssertTrue(message = "최소 희망 금액이 최대 희망 금액보다 클 수 없습니다.")
    private boolean isValidDesiredAmount() {
        if (desiredMinAmount != null && desiredMaxAmount != null) {
            return isPositiveAmount() && isBiggerThanMinAmount();
        }
        return true;
    }

    private boolean isPositiveAmount() {
        return desiredMinAmount >= 0 && desiredMaxAmount >= 0;
    }

    private boolean isBiggerThanMinAmount() {
        return desiredMaxAmount >= desiredMinAmount;
    }

    @AssertTrue(message = "pageNumber는 0보타 커야 합니다.")
    private boolean isValidPageNumber() {
        return pageNumber >= 0;
    }

    public static AcademyFilterWithScrollParam to(AcademyFilterWithScrollRequest request) {
        return new AcademyFilterWithScrollParam(
                request.lat,
                request.lng,
                request.categories(),
                request.desiredMinAmount,
                request.desiredMaxAmount,
                request.pageNumber
        );
    }
}
