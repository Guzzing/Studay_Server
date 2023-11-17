package org.guzzing.studayserver.domain.calendar.controller.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import org.guzzing.studayserver.domain.calendar.controller.dto.request.validation.ValidAttendanceDate;

@Getter
@ValidAttendanceDate
public class AttendanceDate {

    private String startDateOfAttendance;

    private String endDateOfAttendance;

    @NotBlank
    public AttendanceDate(String startDateOfAttendance, String endDateOfAttendance) {
        this.startDateOfAttendance = startDateOfAttendance;
        this.endDateOfAttendance = endDateOfAttendance;
    }
}
