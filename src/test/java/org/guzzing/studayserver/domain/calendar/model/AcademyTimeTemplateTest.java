package org.guzzing.studayserver.domain.calendar.model;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;

import org.guzzing.studayserver.testutil.fixture.academycalender.AcademyCalenderFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


class AcademyTimeTemplateTest {

    private static final int MAX_DIFFERENCE_YEAR = 3;

    @Test
    @DisplayName("등원 일정의 간격이 설정한 최대 년수를 넘어가면 예외를 던진다.")
    void changeEndDateOfAttendance_afterMaxDifferenceYear_throwException() {
        //Given
        AcademyTimeTemplate academyTimeTemplate
                = AcademyCalenderFixture.fridayAcademyTimeTemplate();
        LocalDate startDateOfAttendance = academyTimeTemplate.getStartDateOfAttendance();

        //When & Then
        assertThatThrownBy(() -> academyTimeTemplate.changeEndDateOfAttendance(startDateOfAttendance.plusYears(10)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(String.format("스케줄 일정은 %d년을 넘을 수 없습니다.", MAX_DIFFERENCE_YEAR));
    }

    @Test
    @DisplayName("수정하려고 하는 마지막 등원일자가 등원 시작일보다 이전일 경우 예외를 던진다.")
    void changeEndDateOfAttendance_beforeStartDate_throwException() {
        //Given
        AcademyTimeTemplate academyTimeTemplate
                = AcademyCalenderFixture.fridayAcademyTimeTemplate();
        LocalDate startDateOfAttendance = academyTimeTemplate.getStartDateOfAttendance();

        //When & Then
        assertThatThrownBy(() -> academyTimeTemplate.changeEndDateOfAttendance(startDateOfAttendance.minusDays(10)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("마지막 등원일이 시작 등원일보다 이전일 수는 없습니다.");
    }

}
