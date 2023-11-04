package org.guzzing.studayserver.domain.academy.model;

import org.guzzing.studayserver.testutil.fixture.academy.AcademyFixture;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LessonTest {

    @Test
    void makeLesson_nullAcademy_throwException() {
        //Then
        assertThatThrownBy(
                () -> Lesson.of(null,"자바와 객체지향","자바와 객체지향으로 떠나자","20","1개월","100000")
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void makeLesson_minusCapacity_throwException() {
        //Then
        assertThatThrownBy(
                () -> Lesson.of(AcademyFixture.academySungnam(),"자바와 객체지향","자바와 객체지향으로 떠나자","-100","1개월","100000")
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void makeLesson_minusTotalFee_throwException() {
        //Then
        assertThatThrownBy(
                () -> Lesson.of(AcademyFixture.academySungnam(),"자바와 객체지향","자바와 객체지향으로 떠나자","20","1개월","-100000")
        ).isInstanceOf(IllegalArgumentException.class);
    }


}

