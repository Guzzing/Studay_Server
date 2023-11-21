package org.guzzing.studayserver.domain.calendarInfo.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.guzzing.studayserver.domain.academy.service.LessonReadService;
import org.guzzing.studayserver.domain.academy.service.dto.result.LessonFindByIdsResults;
import org.guzzing.studayserver.domain.calendar.service.AcademySchedulesReadService;
import org.guzzing.studayserver.domain.calendar.service.dto.result.AcademyScheduleFindByDateResults;
import org.guzzing.studayserver.domain.calendar.service.dto.result.AcademyScheduleFindByDateResults.AcademyScheduleFindByDateResult;
import org.guzzing.studayserver.domain.calendar.service.dto.result.AcademyScheduleYearMonthResults;
import org.guzzing.studayserver.domain.calendar.service.dto.result.CalendarFindSchedulesByDateResults;
import org.guzzing.studayserver.domain.calendar.service.dto.result.CalendarFindSchedulesByDateResults.CalendarFindSchedulesByDateResult;
import org.guzzing.studayserver.domain.calendarInfo.service.param.CalendarYearMonthMarkParam;
import org.guzzing.studayserver.domain.calendarInfo.service.result.CalendarYearMonthMarkResult;
import org.guzzing.studayserver.domain.calendarInfo.service.result.CalendarFindSchedulesByDateIncompleteResult;
import org.guzzing.studayserver.domain.calendarInfo.service.util.DateUtility;
import org.guzzing.studayserver.domain.dashboard.service.DashboardReadService;
import org.guzzing.studayserver.domain.dashboard.service.dto.response.DashBoardFindByIdsResults;
import org.guzzing.studayserver.domain.dashboard.service.dto.response.DashBoardFindByIdsResults.DashBoardFindByIdsResult;
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
    private final DashboardReadService dashboardReadService;
    private final AcademySchedulesReadService academySchedulesReadService;
    private final LessonReadService lessonReadService;

    public CalendarFacade(MemberService memberService, HolidayService holidayService,
            DashboardReadService dashboardReadService, AcademySchedulesReadService academySchedulesReadService,
            LessonReadService lessonReadService) {
        this.memberService = memberService;
        this.holidayService = holidayService;
        this.dashboardReadService = dashboardReadService;
        this.academySchedulesReadService = academySchedulesReadService;
        this.lessonReadService = lessonReadService;
    }

    @Transactional(readOnly = true)
    public CalendarYearMonthMarkResult findYearMonthMark(CalendarYearMonthMarkParam calendarMonthMarkParam) {
        int lastDayOfMonth = DateUtility.getLastDayOfMonth(
                calendarMonthMarkParam.year(), calendarMonthMarkParam.month());

        HolidayFindByYearMonthResult holidayFindByYearMonthResult = holidayService.findByYearMonth(
                calendarMonthMarkParam.year(), calendarMonthMarkParam.month());

        MemberInformationResult memberInformationResult = memberService.getById(calendarMonthMarkParam.memberId());
        List<Long> childIds = memberInformationResult.childResults().stream()
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

    @Transactional(readOnly = true)
    public CalendarFindSchedulesByDateResults findSchedulesByDate(Long memberId, LocalDate date) {
        MemberInformationResult memberInformationResult = memberService.getById(memberId);

        List<Long> childIds = memberInformationResult.childResults().stream()
                .map(MemberChildInformationResult::childId)
                .toList();
        AcademyScheduleFindByDateResults academyScheduleFindByDateResults = academySchedulesReadService.findByDate(
                childIds, date);

        List<Long> dashboardIds = academyScheduleFindByDateResults.academySchedules().stream()
                .map(AcademyScheduleFindByDateResult::dashboardId)
                .toList();
        DashBoardFindByIdsResults dashBoardFindByIdsResults = dashboardReadService.findByIds(dashboardIds);

        List<Long> lessonIds = dashBoardFindByIdsResults.dashBoards().stream()
                .map(DashBoardFindByIdsResult::lessonId)
                .toList();
        LessonFindByIdsResults lessonFindByIdsResults = lessonReadService.findByIds(lessonIds);

        List<CalendarFindSchedulesByDateIncompleteResult> combinedResults = academyScheduleFindByDateResults.academySchedules().stream()
                .flatMap(schedule -> dashBoardFindByIdsResults.dashBoards().stream()
                        .filter(dashboard -> dashboard.dashboardId().equals(schedule.dashboardId()))
                        .map(dashboard -> CalendarFindSchedulesByDateIncompleteResult.of(schedule, dashboard)))
                .toList();

        List<CalendarFindSchedulesByDateResult> finalResults = combinedResults.stream()
                .flatMap(incompleteResult -> lessonFindByIdsResults.lessons().stream()
                        .filter(lesson -> lesson.lessonId().equals(incompleteResult.lessonId()))
                        .map(lesson -> CalendarFindSchedulesByDateResult.of(incompleteResult, lesson)))
                .toList();

        return new CalendarFindSchedulesByDateResults(finalResults);
    }
}
