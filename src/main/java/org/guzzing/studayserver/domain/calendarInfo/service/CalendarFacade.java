package org.guzzing.studayserver.domain.calendarInfo.service;

import org.guzzing.studayserver.domain.calendarInfo.service.param.CalendarYearMonthMarkParam;
import org.guzzing.studayserver.domain.calendarInfo.service.result.CalendarYearMonthMarkResult;
import org.guzzing.studayserver.domain.calendarInfo.service.util.DateUtility;
import org.guzzing.studayserver.domain.holiday.service.HolidayService;
import org.guzzing.studayserver.domain.holiday.service.result.HolidayFindByYearMonthResult;
import org.guzzing.studayserver.domain.member.service.MemberService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CalendarFacade {

    private final MemberService memberService;
    private final HolidayService holidayService;
//    private final AcademyCalendarAccessService academyCalendarAccessService;

    public CalendarFacade(
            MemberService memberService,
            HolidayService holidayService
//            AcademyCalendarAccessService academyCalendarAccessService
    ) {
        this.memberService = memberService;
        this.holidayService = holidayService;
//        this.academyCalendarAccessService = academyCalendarAccessService;
    }

    @Transactional(readOnly = true)
    public CalendarYearMonthMarkResult getYearMonthMark(CalendarYearMonthMarkParam calendarMonthMarkParam) {
        int lastDayOfMonth = DateUtility.getLastDayOfMonth(
                calendarMonthMarkParam.year(), calendarMonthMarkParam.month());

        HolidayFindByYearMonthResult holidayFindByYearMonthResult = holidayService.findByYearMonth(
                calendarMonthMarkParam.year(), calendarMonthMarkParam.month());

//        MemberInformationResult memberInformationResult = memberService.getById(calendarMonthMarkParam.memberId());
//        List<Long> childIds = memberInformationResult.memberChildInformationResults().stream()
//                .map(MemberChildInformationResult::childId)
//                .toList();
//
//        AcademyCalendarChildrenScheduleResult academyCalendarChildrenScheduleResult = academyCalendarAccessService.getChildrenScheduleByYearMonth(
//                childIds, calendarMonthMarkParam.year(), calendarMonthMarkParam.month());

        return CalendarYearMonthMarkResult.of(
                lastDayOfMonth,
                holidayFindByYearMonthResult
//                academyCalendarChildrenScheduleResult
        );
    }
}
