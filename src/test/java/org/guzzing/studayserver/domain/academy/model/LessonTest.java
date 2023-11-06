package org.guzzing.studayserver.domain.academy.model;

import org.guzzing.studayserver.testutil.fixture.academy.AcademyFixture;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class LessonTest {

    @Test
    @DisplayName("수업을 등록할 때 학원이 없으면 예외를 던진다.")
    void makeLesson_nullAcademy_throwException() {
        //Then
        assertThatThrownBy(
                () -> Lesson.of(null, "자바와 객체지향", "자바와 객체지향으로 떠나자", "20", "1개월", "100000")
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("수업 정원이 음수인 경우 예외를 던진다.")
    void makeLesson_minusCapacity_throwException() {
        //Then
        assertThatThrownBy(
                () -> Lesson.of(AcademyFixture.academySungnam(), "자바와 객체지향", "자바와 객체지향으로 떠나자", "-100", "1개월", "100000")
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("강의료가 음수인 경우 예외를 던진다. ")
    void makeLesson_minusTotalFee_throwException() {
        //Then
        assertThatThrownBy(
                () -> Lesson.of(AcademyFixture.academySungnam(), "자바와 객체지향", "자바와 객체지향으로 떠나자", "20", "1개월", "-100000")
        ).isInstanceOf(IllegalArgumentException.class);
    }

}
