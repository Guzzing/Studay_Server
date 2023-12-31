package org.guzzing.studayserver.domain.academy.model.vo.academyinfo;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class AcademyInfoAboutScheduleDetailTest {

    @Test
    void makeInvalidNameAcademyInfo_throwException() {
        //Then
        assertThatThrownBy(
                () -> AcademyInfo.of("", "000-0000-0000", ShuttleAvailability.AVAILABLE.name(), "예능(대)")
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void makeInvalidPhoneNumberAcademyInfo_throwException() {
        //Then
        assertThatThrownBy(
                () -> AcademyInfo.of("박세영 코딩학원", "123445667", ShuttleAvailability.AVAILABLE.name(), "예능(대)")
        ).isInstanceOf(IllegalArgumentException.class);
    }

}
