package org.guzzing.studayserver.domain.dashboard.fixture;

import static java.time.DayOfWeek.FRIDAY;
import static java.time.DayOfWeek.MONDAY;
import static java.time.DayOfWeek.SUNDAY;
import static org.guzzing.studayserver.domain.dashboard.model.vo.Repeatance.BIWEEKLY;
import static org.guzzing.studayserver.domain.dashboard.model.vo.Repeatance.DAILY;
import static org.guzzing.studayserver.domain.dashboard.model.vo.Repeatance.WEEKLY;
import static org.guzzing.studayserver.domain.dashboard.model.vo.SimpleMemoType.CHEAP_FEE;
import static org.guzzing.studayserver.domain.dashboard.model.vo.SimpleMemoType.GOOD_FACILITY;
import static org.guzzing.studayserver.domain.dashboard.model.vo.SimpleMemoType.GOOD_MANAGEMENT;
import static org.guzzing.studayserver.domain.dashboard.model.vo.SimpleMemoType.KINDNESS;
import static org.guzzing.studayserver.domain.dashboard.model.vo.SimpleMemoType.LOVELY_TEACHING;
import static org.guzzing.studayserver.domain.dashboard.model.vo.SimpleMemoType.SHUTTLE_AVAILABILITY;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import org.guzzing.studayserver.domain.dashboard.controller.dto.request.DashboardPostRequest;
import org.guzzing.studayserver.domain.dashboard.controller.dto.request.DashboardPutRequest;
import org.guzzing.studayserver.domain.dashboard.controller.vo.Schedule;
import org.guzzing.studayserver.domain.dashboard.controller.vo.SimpleMemo;
import org.guzzing.studayserver.domain.dashboard.model.Dashboard;
import org.guzzing.studayserver.domain.dashboard.model.DashboardSchedule;
import org.guzzing.studayserver.domain.dashboard.model.dto.PaymentInfo;
import org.guzzing.studayserver.domain.dashboard.model.vo.FeeInfo;
import org.guzzing.studayserver.domain.dashboard.repository.DashboardRepository;
import org.guzzing.studayserver.domain.dashboard.service.dto.request.DashboardPostParam;
import org.guzzing.studayserver.domain.dashboard.service.dto.request.DashboardPutParam;
import org.guzzing.studayserver.domain.dashboard.service.vo.AcademyInfo;
import org.guzzing.studayserver.domain.dashboard.service.vo.ChildInfo;
import org.guzzing.studayserver.domain.dashboard.service.vo.LessonInfo;
import org.guzzing.studayserver.domain.dashboard.service.vo.ScheduleInfo;
import org.guzzing.studayserver.domain.dashboard.service.vo.ScheduleInfos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DashboardFixture {

    @Autowired
    private DashboardRepository dashboardRepository;

    public static final long childId = 1L;
    public static final long academyId = 1L;
    public static final long lessonId = 1L;

    public DashboardPostRequest makePostRequest() {
        return new DashboardPostRequest(
                childId, academyId, lessonId,
                List.of(new Schedule(6, "12:00", "21:04", "BIWEEKLY")),
                new PaymentInfo(1_000L, 2_000L, 3_000L, 4_000L, LocalDate.now()),
                new SimpleMemo(true, false, false, true, false, true));
    }

    public DashboardPostParam makePostParam() {
        return new DashboardPostParam(
                1L, 1L, 1L,
                new ScheduleInfos(List.of(
                        new ScheduleInfo(null, "14:00", "18:00", DAILY),
                        new ScheduleInfo(SUNDAY, "12:30", "12:04", BIWEEKLY))),
                new PaymentInfo(1_000L, 2_000L, 3_000L, 4_000L, LocalDate.now()),
                Map.of(
                        KINDNESS, true,
                        GOOD_FACILITY, false,
                        GOOD_MANAGEMENT, true,
                        CHEAP_FEE, true,
                        LOVELY_TEACHING, false,
                        SHUTTLE_AVAILABILITY, true));
    }

    public DashboardPutRequest makePutRequest() {
        return new DashboardPutRequest(
                new PaymentInfo(4_000L, 4_000L, 4_000L, 4_000L, LocalDate.now()),
                new SimpleMemo(true, false, false, true, false, true)
        );
    }

    public DashboardPutParam makePutParam(final long dashboardId) {
        return new DashboardPutParam(
                dashboardId,
                new PaymentInfo(4_000L, 4_000L, 4_000L, 4_000L, LocalDate.now()),
                Map.of(
                        KINDNESS, false,
                        GOOD_FACILITY, true,
                        GOOD_MANAGEMENT, true,
                        CHEAP_FEE, false,
                        LOVELY_TEACHING, true,
                        SHUTTLE_AVAILABILITY, true)
        );
    }

    public Dashboard makeEntity() {
        return new Dashboard(
                1L, 1L, 1L,
                List.of(new DashboardSchedule(MONDAY, "14:00", "18:00", WEEKLY),
                        new DashboardSchedule(FRIDAY, "13:30", "15:40", BIWEEKLY)),
                new FeeInfo(1_000L, 1_000L, 1_000L, 1_000L, LocalDate.of(LocalDate.now().getYear(), 4, 23)),
                List.of(CHEAP_FEE, LOVELY_TEACHING),
                true, false
        );
    }

    public Dashboard makeNotActiveEntity() {
        return new Dashboard(
                1L, 1L, 1L,
                List.of(new DashboardSchedule(MONDAY, "14:00", "18:00", WEEKLY),
                        new DashboardSchedule(FRIDAY, "13:30", "15:40", BIWEEKLY)),
                new FeeInfo(1_000L, 1_000L, 1_000L, 1_000L, LocalDate.of(LocalDate.now().getYear(), 4, 23)),
                List.of(CHEAP_FEE, LOVELY_TEACHING),
                false, false
        );
    }

    public ChildInfo makeChildInfo() {
        return new ChildInfo(childId, "홍길동");
    }

    public AcademyInfo makeAcademyInfo() {
        return new AcademyInfo(
                academyId,
                "메가스터디",
                "010-1234-4321",
                "서울특별시 동작구 노량진동 메가스터디타워",
                "AVAILABLE",
                100_000,
                LocalDate.now(),
                "예능(대)"
        );
    }

    public LessonInfo makeLessonInfo() {
        return new LessonInfo(
                lessonId,
                "국어",
                20,
                "1개월",
                120_000
        );
    }

    public Dashboard createActiveEntity() {
        final Dashboard dashboard = this.makeEntity();

        return dashboardRepository.save(dashboard);
    }

    public Dashboard createNotActiveEntity() {
        final Dashboard dashboard = this.makeNotActiveEntity();

        return dashboardRepository.save(dashboard);
    }

    public List<Dashboard> findAll() {
        return dashboardRepository.findAll();
    }

}
