package org.guzzing.studayserver.testutil.fixture.academycalender;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.guzzing.studayserver.domain.calendar.controller.dto.request.AcademyCalendarCreateRequest;
import org.guzzing.studayserver.domain.calendar.controller.dto.request.AcademyCalendarUpdateRequest;
import org.guzzing.studayserver.domain.calendar.controller.dto.request.LessonTime;
import org.guzzing.studayserver.domain.calendar.model.AcademySchedule;
import org.guzzing.studayserver.domain.calendar.model.AcademyTimeTemplate;
import org.guzzing.studayserver.domain.calendar.model.Periodicity;
import org.guzzing.studayserver.domain.calendar.service.dto.RepeatPeriod;
import org.guzzing.studayserver.domain.calendar.service.dto.param.AcademyCalendarCreateParam;
import org.guzzing.studayserver.domain.calendar.service.dto.param.AcademyCalendarDeleteByDashboardParam;
import org.guzzing.studayserver.domain.calendar.service.dto.param.AcademyCalendarDeleteParam;
import org.guzzing.studayserver.domain.calendar.service.dto.param.AcademyCalendarUpdateParam;
import org.guzzing.studayserver.domain.calendar.service.dto.param.LessonScheduleParam;
import org.guzzing.studayserver.domain.dashboard.service.dto.response.DashboardScheduleAccessResult;
import org.guzzing.studayserver.domain.dashboard.service.access.dto.LessonScheduleAccessResult;

public class AcademyCalenderFixture {

    private static final LocalDate START_DATE_OF_ATTENDANCE = LocalDate.of(2023, 11, 15);
    private static final LocalDate END_DATE_OF_ATTENDANCE_WITH_ONE_YEAR = LocalDate.of(2024, 11, 15);
    private static final LocalDate END_DATE_OF_ATTENDANCE_WITH_TWO_YEAR = LocalDate.of(2025, 11, 15);
    private static final LocalDate END_DATE_OF_ATTENDANCE_WITH_TWO_DAYS = LocalDate.of(2023, 11, 21);
    private static final Periodicity WEEKLY_PERIODICITY = Periodicity.WEEKLY;
    private static final Long FIRST_CHILD_ID = 1L;
    private static final Long FIRST_CHILD_DASH_BOARD_ID = 1L;
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

    public static LessonScheduleParam mondayDashboardScheduleParam() {
        return new LessonScheduleParam(DayOfWeek.MONDAY, LocalTime.of(18, 0), LocalTime.of(20, 0));
    }

    public static LessonScheduleParam fridayDashboardScheduleParam() {
        return new LessonScheduleParam(DayOfWeek.FRIDAY, LocalTime.of(18, 0), LocalTime.of(20, 0));
    }

    public static AcademyCalendarDeleteByDashboardParam academyCalendarDeleteByDashboardParam() {
        return new AcademyCalendarDeleteByDashboardParam(FIRST_CHILD_DASH_BOARD_ID, LocalDate.of(2023, 11, 20));
    }

    public static AcademyCalendarCreateParam firstChildAcademyCalenderCreateParam() {
        return new AcademyCalendarCreateParam(
                List.of(mondayDashboardScheduleParam(), fridayDashboardScheduleParam()),
                START_DATE_OF_ATTENDANCE,
                END_DATE_OF_ATTENDANCE_WITH_ONE_YEAR,
                false,
                FIRST_CHILD_ID,
                FIRST_CHILD_DASH_BOARD_ID,
                "매월 20일마다 상담 진행"

        );
    }

    public static AcademyCalendarCreateParam overlapAcademyCalenderCreateParam() {
        return new AcademyCalendarCreateParam(
                List.of(mondayDashboardScheduleParam(), fridayDashboardScheduleParam()),
                START_DATE_OF_ATTENDANCE,
                END_DATE_OF_ATTENDANCE_WITH_ONE_YEAR,
                false,
                FIRST_CHILD_ID,
                FIRST_CHILD_DASH_BOARD_ID,
                "매월 20일마다 상담 진행"
        );
    }

    public static AcademyCalendarCreateParam notSameOverlapAcademyCalenderCreateParam() {
        return new AcademyCalendarCreateParam(
                List.of(mondayDashboardScheduleParam(), fridayDashboardScheduleParam()),
                START_DATE_OF_ATTENDANCE,
                END_DATE_OF_ATTENDANCE_WITH_ONE_YEAR,
                false,
                FIRST_CHILD_ID,
                FIRST_CHILD_DASH_BOARD_ID + 1L,
                "매월 20일마다 상담 진행"
        );
    }

    public static AcademyCalendarDeleteParam isAfterAcademyCalendarDeleteParam(
            Long academyScheduleId) {
        return new AcademyCalendarDeleteParam(
                academyScheduleId,
                true
        );
    }

    public static AcademyCalendarDeleteParam isOnlyThatAcademyCalendarDeleteParam(
            Long academyScheduleId) {
        return new AcademyCalendarDeleteParam(
                academyScheduleId,
                false
        );
    }

    public static RepeatPeriod fridayRepeatPeriod() {
        return RepeatPeriod.of(
                START_DATE_OF_ATTENDANCE,
                END_DATE_OF_ATTENDANCE_WITH_ONE_YEAR,
                fridayDashboardScheduleParam().dayOfWeek()
        );
    }


    public static RepeatPeriod mondayRepeatPeriod() {
        return RepeatPeriod.of(
                START_DATE_OF_ATTENDANCE,
                END_DATE_OF_ATTENDANCE_WITH_ONE_YEAR,
                mondayDashboardScheduleParam().dayOfWeek()
        );
    }

    public static AcademyTimeTemplate mondayAcademyTimeTemplate() {
        return AcademyTimeTemplate.of(
                mondayDashboardScheduleParam().dayOfWeek(),
                START_DATE_OF_ATTENDANCE,
                END_DATE_OF_ATTENDANCE_WITH_TWO_DAYS,
                false,
                FIRST_CHILD_ID,
                FIRST_CHILD_DASH_BOARD_ID,
                "매월 20일마다 상담 진행"
        );
    }

    public static AcademyTimeTemplate fridayAcademyTimeTemplate() {
        return AcademyTimeTemplate.of(
                fridayDashboardScheduleParam().dayOfWeek(),
                START_DATE_OF_ATTENDANCE,
                END_DATE_OF_ATTENDANCE_WITH_TWO_DAYS,
                false,
                FIRST_CHILD_ID,
                FIRST_CHILD_DASH_BOARD_ID,
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


    public static AcademyCalendarUpdateParam isAllUpdatedAcademyCalendarUpdateParam() {
        return new AcademyCalendarUpdateParam(
                mondayDashboardScheduleParam(),
                START_DATE_OF_ATTENDANCE,
                END_DATE_OF_ATTENDANCE_WITH_TWO_YEAR,
                false,
                1L,
                FIRST_CHILD_ID,
                FIRST_CHILD_DASH_BOARD_ID,
                "매월 20일마다 상담 진행",
                true
        );
    }

    public static AcademyCalendarUpdateParam isNotAllUpdatedAcademyCalendarUpdateParam() {
        return new AcademyCalendarUpdateParam(
               mondayDashboardScheduleParam(),
                LocalDate.of(2023, 12, 14),
                END_DATE_OF_ATTENDANCE_WITH_TWO_YEAR,
                false,
                1L,
                FIRST_CHILD_ID,
                FIRST_CHILD_DASH_BOARD_ID,
                "매월 20일마다 상담 진행",
                false
        );
    }

    public static RepeatPeriod isAllUpdatedFridayRepeatPeriod() {
        return RepeatPeriod.of(
                START_DATE_OF_ATTENDANCE,
                END_DATE_OF_ATTENDANCE_WITH_TWO_YEAR,
                fridayDashboardScheduleParam().dayOfWeek()
        );
    }


    public static RepeatPeriod isAllUpdatedMondayRepeatPeriod() {
        return RepeatPeriod.of(
                START_DATE_OF_ATTENDANCE,
                END_DATE_OF_ATTENDANCE_WITH_TWO_YEAR,
                mondayDashboardScheduleParam().dayOfWeek()
        );
    }

    public static RepeatPeriod isExistedFridayRepeatPeriod() {
        return RepeatPeriod.of(
                START_DATE_OF_ATTENDANCE,
                LocalDate.of(2023, 12, 13),
                fridayDashboardScheduleParam().dayOfWeek()
        );
    }


    public static RepeatPeriod isExisteddMondayRepeatPeriod() {
        return RepeatPeriod.of(
                START_DATE_OF_ATTENDANCE,
                LocalDate.of(2023, 12, 13),
                mondayDashboardScheduleParam().dayOfWeek()
        );
    }


    public static RepeatPeriod isNotAllUpdatedFridayRepeatPeriod() {
        return RepeatPeriod.of(
                LocalDate.of(2023, 12, 14),
                END_DATE_OF_ATTENDANCE_WITH_TWO_YEAR,
                fridayDashboardScheduleParam().dayOfWeek()
        );
    }


    public static RepeatPeriod isNotAllUpdatedMondayRepeatPeriod() {
        return RepeatPeriod.of(
                LocalDate.of(2023, 12, 14),
                END_DATE_OF_ATTENDANCE_WITH_TWO_YEAR,
                mondayDashboardScheduleParam().dayOfWeek()
        );
    }

}
