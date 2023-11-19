package org.guzzing.studayserver.domain.calendarInfo.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.guzzing.studayserver.domain.holiday.service.result.HolidayFindByYearMonthResult.HolidayResult;
import static org.mockito.BDDMockito.given;

import java.time.LocalDate;
import java.util.List;
import org.guzzing.studayserver.domain.calendar.service.AcademySchedulesReadService;
import org.guzzing.studayserver.domain.calendar.service.dto.result.AcademyScheduleYearMonthResults;
import org.guzzing.studayserver.domain.calendarInfo.service.param.CalendarYearMonthMarkParam;
import org.guzzing.studayserver.domain.calendarInfo.service.result.CalendarYearMonthMarkResult;
import org.guzzing.studayserver.domain.holiday.service.HolidayService;
import org.guzzing.studayserver.domain.holiday.service.result.HolidayFindByYearMonthResult;
import org.guzzing.studayserver.domain.member.service.MemberService;
import org.guzzing.studayserver.domain.member.service.result.MemberInformationResult;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CalendarFacadeTest {

    @Mock
    private MemberService memberService;

    @Mock
    private HolidayService holidayService;

    @Mock
    private AcademySchedulesReadService academySchedulesReadService;

    @InjectMocks
    private CalendarFacade calendarFacade;

    @DisplayName("달력 마크에 필요한 정보를 가져온다.")
    @Test
    void findYearMonthMark_ShouldReturnCorrectResult() {
        // Given
        int year = 2023;
        int month = 5;
        long memberId = 1L;

        CalendarYearMonthMarkParam calendarYearMonthMarkParam = new CalendarYearMonthMarkParam(memberId, year, month);

        List<Long> childIds = List.of(1L, 2L);
        MemberInformationResult memberInformationResult = new MemberInformationResult("nickname", "email@example.com",
                List.of(
                        new MemberInformationResult.MemberChildInformationResult(childIds.get(0), "Child1",
                                "Schedule1"),
                        new MemberInformationResult.MemberChildInformationResult(childIds.get(1), "Child2",
                                "Schedule2")));
        given(memberService.getById(memberId)).willReturn(memberInformationResult);

        HolidayFindByYearMonthResult holidayResult = new HolidayFindByYearMonthResult(
                List.of(
                        new HolidayResult(LocalDate.of(year, month, 5), List.of("Holiday1")),
                        new HolidayResult(LocalDate.of(year, month, 10), List.of("Holiday2"))));
        given(holidayService.findByYearMonth(year, month)).willReturn(holidayResult);

        AcademyScheduleYearMonthResults academyScheduleYearMonthResults = new AcademyScheduleYearMonthResults(
                List.of(
                        new AcademyScheduleYearMonthResults.AcademyScheduleYearMonthResult(
                                1L, 1L, 1L, LocalDate.of(year, month, 5)),
                        new AcademyScheduleYearMonthResults.AcademyScheduleYearMonthResult(
                                2L, 2L, 2L, LocalDate.of(year, month, 5))));
        given(academySchedulesReadService.getScheduleByYearMonth(childIds, year, month))
                .willReturn(academyScheduleYearMonthResults);

        // When
        CalendarYearMonthMarkResult result = calendarFacade.findYearMonthMark(calendarYearMonthMarkParam);

        // Assert
        assertThat(result.lastDayOfMonth()).isEqualTo(31);
        assertThat(result.holidayResults()).hasSize(2);
        assertThat(result.existenceDays()).hasSize(1);
    }
}
