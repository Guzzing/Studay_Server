package org.guzzing.studayserver.domain.calendar.controller.dto.request.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import org.guzzing.studayserver.domain.calendar.controller.dto.request.AttendanceDate;

public class AttendanceDateValidator implements ConstraintValidator<ValidAttendanceDate, AttendanceDate> {

    private static final int MAX_DIFFERENCE_YEAR = 3;

    @Override
    public void initialize(ValidAttendanceDate constraintAnnotation) {
    }

    @Override
    public boolean isValid(AttendanceDate attendanceDate, ConstraintValidatorContext context) {
        if (attendanceDate == null) {
            return true;
        }

        try {
            LocalDate startDate = LocalDate.parse(attendanceDate.getStartDateOfAttendance());
            LocalDate endDate = LocalDate.parse(attendanceDate.getEndDateOfAttendance());

            return !endDate.isAfter(startDate.plusYears(MAX_DIFFERENCE_YEAR)) && !endDate.isBefore(startDate);
        } catch (DateTimeParseException e) {
            return false;
        }

    }
}
