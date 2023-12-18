package org.guzzing.studayserver.domain.calendar.controller.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import org.guzzing.studayserver.domain.calendar.controller.dto.request.validation.ValidAttendanceDate;

@ValidAttendanceDate
public record AttendanceDate(
        String startDateOfAttendance,
        String endDateOfAttendance) {
}
