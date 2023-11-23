package org.guzzing.studayserver.domain.calendarInfo.controller.request;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.util.Set;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class CalendarYearMonthMarkRequestTest {

    private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @ParameterizedTest
    @MethodSource("validInputs")
    void givenValidInput_thenNoConstraintViolations(Integer year, Integer month) {
        // When
        CalendarYearMonthMarkRequest request = new CalendarYearMonthMarkRequest(year, month);

        // Then
        assertThat(validator.validate(request)).isEmpty();
    }

    @ParameterizedTest
    @MethodSource("invalidInputs")
    void givenInvalidInput_thenConstraintViolationIsThrown(Integer year, Integer month) {
        // When
        CalendarYearMonthMarkRequest request = new CalendarYearMonthMarkRequest(year, month);
        Set<ConstraintViolation<CalendarYearMonthMarkRequest>> violations = validator.validate(request);

        assertThat(violations).isNotEmpty();
    }

    private static Stream<Arguments> validInputs() {
        return Stream.of(
                Arguments.of(2000, 1),
                Arguments.of(2100, 12)
        );
    }

    private static Stream<Arguments> invalidInputs() {
        return Stream.of(
                Arguments.of(1999, 1),
                Arguments.of(2101, 12),
                Arguments.of(2023, 0),
                Arguments.of(2023, 13)

        );
    }
}
