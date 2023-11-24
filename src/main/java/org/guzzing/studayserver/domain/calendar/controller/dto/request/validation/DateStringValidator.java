package org.guzzing.studayserver.domain.calendar.controller.dto.request.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateStringValidator implements ConstraintValidator<ValidDateString, String> {

    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final int MAX_MONTH = 12;
    private static final int MAX_DAYS = 31;
    private static final int MIN_YEAR = 2000;
    private static final int MAX_YEAR = 10000;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return false;
        }

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
            LocalDate parsedDate = LocalDate.parse(value, formatter);

            int year = parsedDate.getYear();
            int month = parsedDate.getMonthValue();
            int day = parsedDate.getDayOfMonth();

            if (year < MIN_YEAR || year >= MAX_YEAR || month > MAX_MONTH || day > MAX_DAYS) {
                return false;
            }

        } catch (DateTimeParseException e) {
            return false;
        }

        return true;
    }

}
