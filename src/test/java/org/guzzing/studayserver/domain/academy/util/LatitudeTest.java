package org.guzzing.studayserver.domain.academy.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LatitudeTest {

    @DisplayName("위도 범위에 범어나면 예외를 던진다.")
    @ValueSource(doubles = {32, 100, 44})
    @ParameterizedTest
    void createLatitude_outOfBounds_throwException(double latitude) {
        assertThatThrownBy(
            () -> Longitude.of(latitude))
            .isInstanceOf(IllegalArgumentException.class);
    }

}