package org.guzzing.studayserver.domain.dashboard.model.vo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.guzzing.studayserver.domain.dashboard.model.vo.DayOfWeek.NONE;

import org.guzzing.studayserver.global.exception.DashboardException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DayOfWeekTest {

    @Test
    @DisplayName("요일에 대한 문자열로 요일 enum 구한다.")
    void of_ValidDayOfWeekValue_ReturnDayOfWeek() {
        // Given
        final String value = "MONDAY";

        // When
        final DayOfWeek result = DayOfWeek.of(value);

        // Then
        assertThat(result).isEqualTo(DayOfWeek.MONDAY);
    }

    @Test
    @DisplayName("요일 주기가 아닌 문자열로는 NONE 을 반환한다.")
    void of_InvalidDayOfWeekValue_ThrowException() {
        // Given
        final String value = "가나다";

        // When
        final DayOfWeek result = DayOfWeek.of(value);

        // Then
        assertThat(result).isEqualTo(NONE);
    }

}