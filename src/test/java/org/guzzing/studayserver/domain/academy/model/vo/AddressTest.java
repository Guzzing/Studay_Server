package org.guzzing.studayserver.domain.academy.model.vo;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.guzzing.studayserver.domain.academy.model.vo.academyinfo.AcademyInfo;
import org.guzzing.studayserver.domain.academy.model.vo.academyinfo.ShuttleAvailability;
import org.junit.jupiter.api.Test;

class AddressTest {

    @Test
    void makeInvalidAddress_throwException() {
        //Then
        assertThatThrownBy(
                () -> AcademyInfo.of("박세영 코딩학원", "123456789", ShuttleAvailability.AVAILABLE.name(), "예능(대)")
        ).isInstanceOf(IllegalArgumentException.class);
    }

}
