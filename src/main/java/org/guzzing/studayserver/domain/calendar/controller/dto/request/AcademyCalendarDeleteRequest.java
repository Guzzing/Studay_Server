package org.guzzing.studayserver.domain.calendar.controller.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

import org.guzzing.studayserver.domain.calendar.service.dto.param.AcademyCalendarDeleteParam;

public record AcademyCalendarDeleteRequest(
        @Positive
        @NotNull
        Long academyScheduleId,

        @NotNull
        Boolean isAllDeleted

) {

    public static AcademyCalendarDeleteParam to(AcademyCalendarDeleteRequest academyCalendarDeleteRequest) {
        return new AcademyCalendarDeleteParam(
                academyCalendarDeleteRequest.academyScheduleId(),
                academyCalendarDeleteRequest.isAllDeleted()
        );
    }

}
