package org.guzzing.studayserver.domain.calendarInfo.service;

import java.util.List;
import org.guzzing.studayserver.domain.calendar.service.AcademySchedulesReadService;
import org.guzzing.studayserver.domain.calendar.service.dto.result.AcademyScheduleYearMonthResults;
import org.guzzing.studayserver.domain.calendarInfo.service.param.CalendarYearMonthMarkParam;
import org.guzzing.studayserver.domain.calendarInfo.service.result.CalendarYearMonthMarkResult;
import org.guzzing.studayserver.domain.calendarInfo.service.util.DateUtility;
import org.guzzing.studayserver.domain.holiday.service.HolidayService;
import org.guzzing.studayserver.domain.holiday.service.result.HolidayFindByYearMonthResult;
import org.guzzing.studayserver.domain.member.service.MemberService;
import org.guzzing.studayserver.domain.member.service.result.MemberInformationResult;
import org.guzzing.studayserver.domain.member.service.result.MemberInformationResult.MemberChildInformationResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CalendarFacade {

    private final MemberService memberService;
    private final HolidayService holidayService;
    private final AcademySchedulesReadService academySchedulesReadService;

    public CalendarFacade(
            MemberService memberService,
            HolidayService holidayService,
            AcademySchedulesReadService academySchedulesReadService
    ) {
        this.memberService = memberService;
        this.holidayService = holidayService;
        this.academySchedulesReadService = academySchedulesReadService;
    }

    @Transactional(readOnly = true)
    public CalendarYearMonthMarkResult getYearMonthMark(CalendarYearMonthMarkParam calendarMonthMarkParam) {
        int lastDayOfMonth = DateUtility.getLastDayOfMonth(
                calendarMonthMarkParam.year(), calendarMonthMarkParam.month());

        HolidayFindByYearMonthResult holidayFindByYearMonthResult = holidayService.findByYearMonth(
                calendarMonthMarkParam.year(), calendarMonthMarkParam.month());

        MemberInformationResult memberInformationResult = memberService.getById(calendarMonthMarkParam.memberId());
        List<Long> childIds = memberInformationResult.memberChildInformationResults().stream()
                .map(MemberChildInformationResult::childId)
                .toList();

        AcademyScheduleYearMonthResults academyScheduleYearMonthResults = academySchedulesReadService.getScheduleByYearMonth(
                childIds, calendarMonthMarkParam.year(), calendarMonthMarkParam.month());

        return CalendarYearMonthMarkResult.of(
                lastDayOfMonth,
                holidayFindByYearMonthResult,
                academyScheduleYearMonthResults
        );
    }
}
