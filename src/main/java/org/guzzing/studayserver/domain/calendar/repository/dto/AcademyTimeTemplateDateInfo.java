package org.guzzing.studayserver.domain.calendar.repository.dto;

import java.time.LocalDate;

public interface AcademyTimeTemplateDateInfo {

    LocalDate getStartDateOfAttendance();

    LocalDate getEndDateOfAttendance();

    Long getId();

}
