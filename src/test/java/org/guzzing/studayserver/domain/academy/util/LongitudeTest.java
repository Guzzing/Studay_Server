package org.guzzing.studayserver.domain.academy.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LongitudeTest {

    @DisplayName("경도 범위에 범어나면 예외를 던진다.")
    @ValueSource(doubles = {200, 123, 133})
    @ParameterizedTest
    void createLongitude_outOfBounds_throwException(double longitude) {
        assertThatThrownBy(
            () -> Longitude.of(longitude))
            .isInstanceOf(IllegalArgumentException.class);
    }

}