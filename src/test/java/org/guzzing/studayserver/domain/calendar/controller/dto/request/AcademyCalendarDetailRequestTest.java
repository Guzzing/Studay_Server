package org.guzzing.studayserver.domain.calendar.controller.dto.request;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AcademyCalendarDetailRequestTest {

    @Test
    @DisplayName("아이디가 음수인 경우 예외를 던진다.")
    void construct_invalidLessonId_throwException() {
        assertThatThrownBy(
                () -> new AcademyCalendarDetailRequest(
                        -1L,
                        1L)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("아이 아이디가 음수 경우 예외를 던진다.")
    void construct_invalidChildrenID_throwException() {
        assertThatThrownBy(
                () -> new AcademyCalendarDetailRequest(
                        1L,
                        -1L
                )
        ).isInstanceOf(IllegalArgumentException.class);
    }

}
