package org.guzzing.studayserver.domain.acdademycalendar.repository.dto;

import java.time.LocalDate;

public interface AcademyTimeTemplateDateInfo {

    LocalDate getStartDateOfAttendance();
    LocalDate getEndDateOfAttendance();
    Long getId();

}
