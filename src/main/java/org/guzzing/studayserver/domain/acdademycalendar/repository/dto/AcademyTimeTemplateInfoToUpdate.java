package org.guzzing.studayserver.domain.acdademycalendar.repository.dto;

import jakarta.persistence.Column;

import java.time.LocalDate;

public interface AcademyTimeTemplateInfoToUpdate {

  LocalDate getStartDateOfAttendance();

  LocalDate getEndDateOfAttendance();

  boolean isAlarmed();

  String getMemo();

  Long getDashboardId();

}
