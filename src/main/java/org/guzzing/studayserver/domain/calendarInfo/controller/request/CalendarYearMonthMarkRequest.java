package org.guzzing.studayserver.domain.calendarInfo.controller.request;

import jakarta.validation.constraints.NotNull;
import org.guzzing.studayserver.domain.calendarInfo.service.param.CalendarYearMonthMarkParam;
import org.hibernate.validator.constraints.Range;

public record CalendarYearMonthMarkRequest(
        @Range(min = 2000, max = 2100)
        @NotNull
        Integer year,

        @Range(min = 1, max = 12)
        @NotNull
        Integer month
) {


    public CalendarYearMonthMarkParam toParam(Long memberId) {
        return new CalendarYearMonthMarkParam(
                memberId,
                year,
                month
        );
    }
}
