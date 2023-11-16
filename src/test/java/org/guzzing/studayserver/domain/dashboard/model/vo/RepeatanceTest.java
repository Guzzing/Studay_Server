package org.guzzing.studayserver.domain.dashboard.model.vo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.guzzing.studayserver.global.exception.DashboardException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RepeatanceTest {

    @Test
    @DisplayName("반복 주기에 대한 문자열로 반복 enum을 구한다.")
    void of_ValidRepeatanceValue_ReturnRepeatance() {
        // Given
        final String value = "NONE";

        // When
        final Repeatance result = Repeatance.of(value);

        // Then
        assertThat(result).isEqualTo(Repeatance.NONE);
    }

    @Test
    @DisplayName("반복 주기가 아닌 문자열로 반복 enum을 구할 수 없다.")
    void of_InvalidRepeatanceValue_ThrowException() {
        // Given
        final String value = "가나다";

        // When & Then
        assertThatThrownBy(() -> Repeatance.of(value))
                .isInstanceOf(DashboardException.class)
                .hasMessage("설정한 반복 주기와 일치하는 주기가 없습니다.");
    }
}