package org.guzzing.studayserver.domain.calendarInfo.service;

import java.util.List;
import org.guzzing.studayserver.domain.calendar.service.AcademyCalendarAccessService;
import org.guzzing.studayserver.domain.calendarInfo.service.param.CalendarYearMonthMarkParam;
import org.guzzing.studayserver.domain.calendarInfo.service.result.CalendarYearMonthMarkResult;
import org.guzzing.studayserver.domain.calendarInfo.service.util.DateUtility;
import org.guzzing.studayserver.domain.member.service.MemberService;
import org.guzzing.studayserver.domain.member.service.result.MemberInformationResult;
import org.guzzing.studayserver.domain.member.service.result.MemberInformationResult.MemberChildInformationResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CalendarFacade {

    private final DateUtility dateUtility;
    private final MemberService memberService;
//    private final HolidayService holidayService;
//    private final AcademyCalendarAccessService academyCalendarAccessService;

    public CalendarFacade(
            DateUtility dateUtility,
            MemberService memberService
//            HolidayService holidayService,
//            AcademyCalendarAccessService academyCalendarAccessService
    ) {
        this.dateUtility = dateUtility;
        this.memberService = memberService;
//        this.holidayService = holidayService;
//        this.academyCalendarAccessService = academyCalendarAccessService;
    }

    @Transactional(readOnly = true)
    public CalendarYearMonthMarkResult getYearMonthMark(CalendarYearMonthMarkParam calendarMonthMarkParam) {
        int lastDayOfMonth = dateUtility.getLastDayOfMonth(
                calendarMonthMarkParam.year(), calendarMonthMarkParam.month());

//        HolidayFindByYearMonthResult holidayFindByYearMonthResult = holidayService.findByYearMonth(
//                calendarMonthMarkParam.year(), calendarMonthMarkParam.month());
//
//        MemberInformationResult memberInformationResult = memberService.getById(calendarMonthMarkParam.memberId());
//        List<Long> childIds = memberInformationResult.memberChildInformationResults().stream()
//                .map(MemberChildInformationResult::childId)
//                .toList();
//
//        AcademyCalendarChildrenScheduleResult academyCalendarChildrenScheduleResult = academyCalendarAccessService.getChildrenScheduleByYearMonth(
//                childIds, calendarMonthMarkParam.year(), calendarMonthMarkParam.month());

        return CalendarYearMonthMarkResult.of(
                lastDayOfMonth
//                holidayFindByYearMonthResult,
//                academyCalendarChildrenScheduleResult
        );
    }
}
