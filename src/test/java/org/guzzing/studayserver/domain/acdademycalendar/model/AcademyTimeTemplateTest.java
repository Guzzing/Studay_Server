package org.guzzing.studayserver.domain.acdademycalendar.model;

import org.guzzing.studayserver.testutil.fixture.academycalender.AcademyCalenderFixture;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThatThrownBy;


class AcademyTimeTemplateTest {

    private static final int MAX_DIFFERENCE_YEAR = 3;

    @Test
    void changeEndDateOfAttendance_afterMaxDifferenceYear_throwException() {
        //Given
        AcademyTimeTemplate academyTimeTemplate = AcademyCalenderFixture.fridayAcademyTimeTemplate();
        LocalDate startDateOfAttendance = academyTimeTemplate.getStartDateOfAttendance();

        //When & Then
        assertThatThrownBy(() -> academyTimeTemplate.changeEndDateOfAttendance(startDateOfAttendance.plusYears(10)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(String.format("스케줄 일정은 %d년을 넘을 수 없습니다.", MAX_DIFFERENCE_YEAR));
    }

    @Test
    void changeEndDateOfAttendance_beforeStartDate_throwException() {
        //Given
        AcademyTimeTemplate academyTimeTemplate = AcademyCalenderFixture.fridayAcademyTimeTemplate();
        LocalDate startDateOfAttendance = academyTimeTemplate.getStartDateOfAttendance();

        //When & Then
        assertThatThrownBy(() -> academyTimeTemplate.changeEndDateOfAttendance(startDateOfAttendance.minusDays(10)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("마지막 등원일이 시작 등원일보다 이전일 수는 없습니다.");
    }

}
