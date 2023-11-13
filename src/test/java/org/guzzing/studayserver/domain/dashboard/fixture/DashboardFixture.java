package org.guzzing.studayserver.domain.dashboard.fixture;

import static org.guzzing.studayserver.domain.dashboard.model.vo.DayOfWeek.FRIDAY;
import static org.guzzing.studayserver.domain.dashboard.model.vo.DayOfWeek.MONDAY;
import static org.guzzing.studayserver.domain.dashboard.model.vo.DayOfWeek.NONE;
import static org.guzzing.studayserver.domain.dashboard.model.vo.DayOfWeek.SATURDAY;
import static org.guzzing.studayserver.domain.dashboard.model.vo.Repeatance.BIWEEKLY;
import static org.guzzing.studayserver.domain.dashboard.model.vo.Repeatance.DAILY;
import static org.guzzing.studayserver.domain.dashboard.model.vo.Repeatance.WEEKLY;
import static org.guzzing.studayserver.domain.dashboard.model.vo.SimpleMemoType.CHEAP_FEE;
import static org.guzzing.studayserver.domain.dashboard.model.vo.SimpleMemoType.LOVELY_TEACHING;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import org.guzzing.studayserver.domain.dashboard.controller.dto.request.DashboardPostRequest;
import org.guzzing.studayserver.domain.dashboard.controller.vo.Schedule;
import org.guzzing.studayserver.domain.dashboard.controller.vo.SimpleMemo;
import org.guzzing.studayserver.domain.dashboard.model.Dashboard;
import org.guzzing.studayserver.domain.dashboard.model.DashboardSchedule;
import org.guzzing.studayserver.domain.dashboard.model.dto.PaymentInfo;
import org.guzzing.studayserver.domain.dashboard.model.vo.FeeInfo;
import org.guzzing.studayserver.domain.dashboard.model.vo.SimpleMemoType;
import org.guzzing.studayserver.domain.dashboard.service.dto.request.DashboardPostParam;
import org.guzzing.studayserver.domain.dashboard.service.vo.AcademyInfo;
import org.guzzing.studayserver.domain.dashboard.service.vo.ChildInfo;
import org.guzzing.studayserver.domain.dashboard.service.vo.LessonInfo;
import org.guzzing.studayserver.domain.dashboard.service.vo.ScheduleInfo;
import org.guzzing.studayserver.domain.dashboard.service.vo.ScheduleInfos;

public class DashboardFixture {

    public static long childId = 1L;
    public static long academyId = 1L;
    public static long lessonId = 1L;

    public static DashboardPostRequest makePostRequest() {
        return new DashboardPostRequest(
                childId, academyId, lessonId,
                List.of(new Schedule("SATURDAY", LocalTime.of(12, 30), LocalTime.of(21, 4), "BIWEEKLY")),
                new PaymentInfo(1_000L, 2_000L, 3_000L, 4_000L, LocalDate.now()),
                new SimpleMemo(true, false, false, true, false, true));
    }

    public static DashboardPostParam makePostParam() {
        return new DashboardPostParam(
                1L, 1L, 1L,
                new ScheduleInfos(List.of(
                        new ScheduleInfo(NONE, LocalTime.of(14, 0), LocalTime.of(18, 0), DAILY),
                        new ScheduleInfo(SATURDAY, LocalTime.of(12, 30), LocalTime.of(21, 4), BIWEEKLY))),
                new PaymentInfo(1_000L, 2_000L, 3_000L, 4_000L, LocalDate.now()),
                Map.of(
                        SimpleMemoType.KINDNESS, true,
                        SimpleMemoType.GOOD_FACILITY, false,
                        SimpleMemoType.GOOD_MANAGEMENT, true,
                        CHEAP_FEE, true,
                        SimpleMemoType.LOVELY_TEACHING, false,
                        SimpleMemoType.SHUTTLE_AVAILABILITY, true));
    }

    public static Dashboard makeEntity() {
        return new Dashboard(
                1L, 1L, 1L,
                List.of(new DashboardSchedule(MONDAY, LocalTime.of(14, 0), LocalTime.of(18, 0), WEEKLY),
                        new DashboardSchedule(FRIDAY, LocalTime.of(13, 30), LocalTime.of(14, 50), BIWEEKLY)),
                new FeeInfo(1_000L, 1_000L, 1_000L, 1_000L, LocalDate.of(LocalDate.now().getYear(), 4, 23)),
                List.of(CHEAP_FEE, LOVELY_TEACHING),
                true, true
        );
    }

    public static ChildInfo makeChildInfo() {
        return new ChildInfo(childId, "홍길동");
    }

    public static AcademyInfo makeAcademyInfo() {
        return new AcademyInfo(academyId, "메가스터디", "서울특별시 동작구 노량진동 메가스터디타워");
    }

    public static LessonInfo makeLessonInfo() {
        return new LessonInfo(lessonId, "국어");
    }

}
