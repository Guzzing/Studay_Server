package org.guzzing.studayserver.testutil.fixture.academycalender;

import org.guzzing.studayserver.domain.calendar.controller.dto.request.AcademyCalendarCreateRequest;
import org.guzzing.studayserver.domain.calendar.controller.dto.request.AcademyCalendarUpdateRequest;
import org.guzzing.studayserver.domain.calendar.controller.dto.request.LessonTime;
import org.guzzing.studayserver.domain.calendar.model.AcademySchedule;
import org.guzzing.studayserver.domain.calendar.model.AcademyTimeTemplate;
import org.guzzing.studayserver.domain.calendar.model.Periodicity;
import org.guzzing.studayserver.domain.calendar.service.dto.RepeatPeriod;
import org.guzzing.studayserver.domain.calendar.service.dto.param.*;
import org.guzzing.studayserver.domain.dashboard.DashboardScheduleAccessResult;
import org.guzzing.studayserver.domain.dashboard.LessonScheduleAccessResult;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class AcademyCalenderFixture {

    private static final LocalDate START_DATE_OF_ATTENDANCE = LocalDate.of(2023, 11, 15);
    private static final LocalDate END_DATE_OF_ATTENDANCE_WITH_ONE_YEAR = LocalDate.of(2024, 11, 15);
    private static final LocalDate END_DATE_OF_ATTENDANCE_WITH_TWO_YEAR = LocalDate.of(2025, 11, 15);
    private static final LocalDate END_DATE_OF_ATTENDANCE_WITH_TWO_DAYS = LocalDate.of(2023, 11, 21);
    private static final Periodicity WEEKLY_PERIODICITY = Periodicity.WEEKLY;
    private static final Long FIRST_CHILD_DASHBOARD_ID = 1L;
    private static final Long SECOND_CHILD_DASHBOARD_ID = 2L;
    private static final Long FIRST_CHILD_ID = 1L;
    private static final Long SECOND_CHILD_ID = 2L;
    private static final LessonTime LESSON_TIME = new LessonTime("18:00", "20:00");

    public static AcademyCalendarCreateRequest.LessonScheduleCreateRequest mondayLessonScheduleCreateRequest() {
        return new AcademyCalendarCreateRequest.LessonScheduleCreateRequest(DayOfWeek.MONDAY.toString(), LESSON_TIME);
    }

    public static AcademyCalendarCreateRequest.LessonScheduleCreateRequest fridayLessonScheduleCreateRequest() {
        return new AcademyCalendarCreateRequest.LessonScheduleCreateRequest(DayOfWeek.FRIDAY.toString(), LESSON_TIME);
    }

    public static AcademyCalendarUpdateRequest.LessonScheduleUpdateRequest mondayLessonScheduleUpdateRequest() {
        return new AcademyCalendarUpdateRequest.LessonScheduleUpdateRequest(DayOfWeek.MONDAY.toString(), LESSON_TIME);
    }

    public static AcademyCalendarUpdateRequest.LessonScheduleUpdateRequest fridayLessonScheduleUpdateRequest() {
        return new AcademyCalendarUpdateRequest.LessonScheduleUpdateRequest(DayOfWeek.FRIDAY.toString(), LESSON_TIME);
    }


    public static LessonScheduleParam mondayDashboardScheduleParam() {
        return new LessonScheduleParam(DayOfWeek.MONDAY, LocalTime.of(18, 0), LocalTime.of(20, 0));
    }

    public static LessonScheduleParam fridayDashboardScheduleParam() {
        return new LessonScheduleParam(DayOfWeek.FRIDAY, LocalTime.of(18, 0), LocalTime.of(20, 0));
    }

    public static AcademyCalendarDeleteByDashboardParam academyCalendarDeleteByDashboardParam() {
        return new AcademyCalendarDeleteByDashboardParam(FIRST_CHILD_DASHBOARD_ID, LocalDate.of(2023, 11, 20));
    }

    public static AcademyCalendarCreateParam firstChildAcademyCalenderCreateParam() {
        return new AcademyCalendarCreateParam(
                List.of(mondayDashboardScheduleParam(), fridayDashboardScheduleParam()),
                START_DATE_OF_ATTENDANCE,
                END_DATE_OF_ATTENDANCE_WITH_ONE_YEAR,
                false,
                1L,
                FIRST_CHILD_ID,
                FIRST_CHILD_DASHBOARD_ID,
                "매월 20일마다 상담 진행",
                WEEKLY_PERIODICITY

        );
    }

    public static AcademyCalendarCreateParam secondChildAcademyCalenderCreateParam() {
        return new AcademyCalendarCreateParam(
                List.of(mondayDashboardScheduleParam(), fridayDashboardScheduleParam()),
                START_DATE_OF_ATTENDANCE,
                END_DATE_OF_ATTENDANCE_WITH_ONE_YEAR,
                false,
                1L,
                SECOND_CHILD_ID,
                SECOND_CHILD_DASHBOARD_ID,
                "슬리퍼 챙기는 날",
                WEEKLY_PERIODICITY

        );
    }

    public static AcademyCalendarDeleteParam isAfterAcademyCalendarDeleteParam(Long academyScheduleId) {
        return new AcademyCalendarDeleteParam(
                FIRST_CHILD_DASHBOARD_ID,
                academyScheduleId,
                true,
                START_DATE_OF_ATTENDANCE.plusDays(30L)
        );
    }

    public static AcademyCalendarDeleteParam isOnlyThatAcademyCalendarDeleteParam(Long academyScheduleId) {
        return new AcademyCalendarDeleteParam(
                FIRST_CHILD_DASHBOARD_ID,
                academyScheduleId,
                false,
                START_DATE_OF_ATTENDANCE.plusDays(30L)
        );
    }

    public static AcademyTimeTemplate overlapAcademyTimeTemplate() {
        return AcademyTimeTemplate.of(
                mondayDashboardScheduleParam().dayOfWeek(),
                START_DATE_OF_ATTENDANCE,
                END_DATE_OF_ATTENDANCE_WITH_ONE_YEAR,
                false,
                1L,
                FIRST_CHILD_ID,
                FIRST_CHILD_DASHBOARD_ID,
                "매월 20일마다 상담 진행"
        );
    }

    public static RepeatPeriod fridayRepeatPeriod() {
        return RepeatPeriod.of(
                START_DATE_OF_ATTENDANCE,
                END_DATE_OF_ATTENDANCE_WITH_ONE_YEAR,
                fridayDashboardScheduleParam().dayOfWeek(),
                WEEKLY_PERIODICITY
        );
    }


    public static RepeatPeriod mondayRepeatPeriod() {
        return RepeatPeriod.of(
                START_DATE_OF_ATTENDANCE,
                END_DATE_OF_ATTENDANCE_WITH_ONE_YEAR,
                mondayDashboardScheduleParam().dayOfWeek(),
                WEEKLY_PERIODICITY
        );
    }

    public static AcademyTimeTemplate mondayAcademyTimeTemplate() {
        return AcademyTimeTemplate.of(
                mondayDashboardScheduleParam().dayOfWeek(),
                START_DATE_OF_ATTENDANCE,
                END_DATE_OF_ATTENDANCE_WITH_TWO_DAYS,
                false,
                1L,
                FIRST_CHILD_ID,
                FIRST_CHILD_DASHBOARD_ID,
                "매월 20일마다 상담 진행"
        );
    }

    public static AcademyTimeTemplate fridayAcademyTimeTemplate() {
        return AcademyTimeTemplate.of(
                fridayDashboardScheduleParam().dayOfWeek(),
                START_DATE_OF_ATTENDANCE,
                END_DATE_OF_ATTENDANCE_WITH_TWO_DAYS,
                false,
                1L,
                FIRST_CHILD_ID,
                FIRST_CHILD_DASHBOARD_ID,
                "매월 20일마다 상담 진행"
        );
    }

    public static AcademySchedule fridayAcademySchedule(AcademyTimeTemplate fridayAcademyTimeTemplate) {
        return AcademySchedule.of(
                fridayAcademyTimeTemplate,
                LocalDate.of(2023, 11, 17),
                fridayDashboardScheduleParam().lessonStartTime(),
                fridayDashboardScheduleParam().lessonEndTime()
        );
    }

    public static AcademySchedule mondayAcademySchedule(AcademyTimeTemplate mondayAcademyTimeTemplate) {
        return AcademySchedule.of(
                mondayAcademyTimeTemplate,
                LocalDate.of(2023, 11, 20),
                mondayDashboardScheduleParam().lessonStartTime(),
                mondayDashboardScheduleParam().lessonEndTime()
        );
    }

    public static DashboardScheduleAccessResult dashboardScheduleAccessResult() {
        return new DashboardScheduleAccessResult(
                "빵빵이",
                "김별 언어학원",
                "Java는 무엇일까?",
                WEEKLY_PERIODICITY,
                List.of(
                        new LessonScheduleAccessResult(
                                fridayDashboardScheduleParam().dayOfWeek(),
                                fridayDashboardScheduleParam().lessonStartTime(),
                                fridayDashboardScheduleParam().lessonEndTime()
                        ),
                        new LessonScheduleAccessResult(
                                mondayDashboardScheduleParam().dayOfWeek(),
                                mondayDashboardScheduleParam().lessonStartTime(),
                                mondayDashboardScheduleParam().lessonEndTime()
                        )
                )
        );
    }

    public static AcademyCalendarUpdateParam isAllUpdatedAcademyCalendarUpdateParam(Long academyScheduleId) {
        return new AcademyCalendarUpdateParam(
                List.of(mondayDashboardScheduleParam(), fridayDashboardScheduleParam()),
                START_DATE_OF_ATTENDANCE,
                END_DATE_OF_ATTENDANCE_WITH_TWO_YEAR,
                false,
                1L,
                FIRST_CHILD_ID,
                FIRST_CHILD_DASHBOARD_ID,
                "매월 20일마다 상담 진행",
                WEEKLY_PERIODICITY,
                true
        );
    }

    public static AcademyCalendarUpdateParam isNotAllUpdatedAcademyCalendarUpdateParam(Long academyScheduleId) {
        return new AcademyCalendarUpdateParam(
                List.of(mondayDashboardScheduleParam(), fridayDashboardScheduleParam()),
                LocalDate.of(2023, 12, 14),
                END_DATE_OF_ATTENDANCE_WITH_TWO_YEAR,
                false,
                1L,
                FIRST_CHILD_ID,
                FIRST_CHILD_DASHBOARD_ID,
                "매월 20일마다 상담 진행",
                WEEKLY_PERIODICITY,
                false
        );
    }

    public static RepeatPeriod isAllUpdatedFridayRepeatPeriod() {
        return RepeatPeriod.of(
                START_DATE_OF_ATTENDANCE,
                END_DATE_OF_ATTENDANCE_WITH_TWO_YEAR,
                fridayDashboardScheduleParam().dayOfWeek(),
                WEEKLY_PERIODICITY
        );
    }


    public static RepeatPeriod isAllUpdatedMondayRepeatPeriod() {
        return RepeatPeriod.of(
                START_DATE_OF_ATTENDANCE,
                END_DATE_OF_ATTENDANCE_WITH_TWO_YEAR,
                mondayDashboardScheduleParam().dayOfWeek(),
                WEEKLY_PERIODICITY
        );
    }

    public static RepeatPeriod isExistedFridayRepeatPeriod() {
        return RepeatPeriod.of(
                START_DATE_OF_ATTENDANCE,
                LocalDate.of(2023, 12, 13),
                fridayDashboardScheduleParam().dayOfWeek(),
                WEEKLY_PERIODICITY
        );
    }


    public static RepeatPeriod isExisteddMondayRepeatPeriod() {
        return RepeatPeriod.of(
                START_DATE_OF_ATTENDANCE,
                LocalDate.of(2023, 12, 13),
                mondayDashboardScheduleParam().dayOfWeek(),
                WEEKLY_PERIODICITY
        );
    }


    public static RepeatPeriod isNotAllUpdatedFridayRepeatPeriod() {
        return RepeatPeriod.of(
                LocalDate.of(2023, 12, 14),
                END_DATE_OF_ATTENDANCE_WITH_TWO_YEAR,
                fridayDashboardScheduleParam().dayOfWeek(),
                WEEKLY_PERIODICITY
        );
    }


    public static RepeatPeriod isNotAllUpdatedMondayRepeatPeriod() {
        return RepeatPeriod.of(
                LocalDate.of(2023, 12, 14),
                END_DATE_OF_ATTENDANCE_WITH_TWO_YEAR,
                mondayDashboardScheduleParam().dayOfWeek(),
                WEEKLY_PERIODICITY
        );
    }

}
