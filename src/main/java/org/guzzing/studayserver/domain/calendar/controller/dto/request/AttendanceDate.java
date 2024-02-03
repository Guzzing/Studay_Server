package org.guzzing.studayserver.domain.calendar.controller.dto.request;

import jakarta.validation.constraints.NotBlank;
import org.guzzing.studayserver.domain.calendar.controller.dto.request.validation.ValidAttendanceDate;

@ValidAttendanceDate
public record AttendanceDate(
        String startDateOfAttendance,
        String endDateOfAttendance
) {
    @NotBlank
    public AttendanceDate(String startDateOfAttendance, String endDateOfAttendance) {
        this.startDateOfAttendance = startDateOfAttendance;
        this.endDateOfAttendance = endDateOfAttendance;
    }

}
