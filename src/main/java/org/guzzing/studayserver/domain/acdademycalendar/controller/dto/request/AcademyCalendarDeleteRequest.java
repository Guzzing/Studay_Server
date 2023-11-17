package org.guzzing.studayserver.domain.acdademycalendar.controller.dto.request;

import jakarta.validation.constraints.NotNull;
import org.guzzing.studayserver.domain.acdademycalendar.service.dto.param.AcademyCalendarDeleteParam;

import java.time.LocalDate;

public record AcademyCalendarDeleteRequest (
        @NotNull
        Long dashboardId,

        @NotNull
        Long academyScheduleId,

        @NotNull
        Boolean isAllDeleted,

        @NotNull
        LocalDate requestDate
) {

    public static AcademyCalendarDeleteParam to(AcademyCalendarDeleteRequest academyCalendarDeleteRequest) {
        return new AcademyCalendarDeleteParam(
                academyCalendarDeleteRequest.dashboardId,
                academyCalendarDeleteRequest.academyScheduleId(),
                academyCalendarDeleteRequest.isAllDeleted(),
                academyCalendarDeleteRequest.requestDate
        );
    }

}
