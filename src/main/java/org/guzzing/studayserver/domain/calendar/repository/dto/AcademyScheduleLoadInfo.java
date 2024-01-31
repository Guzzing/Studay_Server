package org.guzzing.studayserver.domain.calendar.repository.dto;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

public interface AcademyScheduleLoadInfo {

    Long getDashboardId();
    LocalDate getStartDateOfAttendance();
    LocalDate getEndDateOfAttendance();
    boolean getIsAlarmed();
    String getMemo();
    DayOfWeek getDayOfWeek();
    LocalTime getLessonEndTime();
    LocalTime getLessonStartTime();

}
