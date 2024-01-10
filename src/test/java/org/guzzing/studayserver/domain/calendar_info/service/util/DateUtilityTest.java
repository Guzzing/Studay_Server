package org.guzzing.studayserver.domain.calendar_info.service.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class DateUtilityTest {

    @ParameterizedTest
    @MethodSource("datesForLastDayOfMonthProvider")
    void testLastDayOfMonth(int year, int month, int expectedLastDay) {
        // When
        int lastDay = DateUtility.getLastDayOfMonth(year, month);

        // Then
        assertThat(lastDay).isEqualTo(expectedLastDay);
    }

    static Stream<Arguments> datesForLastDayOfMonthProvider() {
        return Stream.of(
                Arguments.of(2021, 1, 31),
                Arguments.of(2021, 2, 28),
                Arguments.of(2020, 2, 29),
                Arguments.of(2021, 4, 30),
                Arguments.of(2021, 6, 30),
                Arguments.of(2021, 9, 30),
                Arguments.of(2021, 11, 30),
                Arguments.of(2021, 12, 31)
        );
    }
}
