package org.guzzing.studayserver.testutil.fixture.dashboard;

import static java.time.DayOfWeek.FRIDAY;
import static java.time.DayOfWeek.MONDAY;
import static java.time.DayOfWeek.SUNDAY;
import static org.guzzing.studayserver.domain.academy.util.CategoryInfo.MATH;
import static org.guzzing.studayserver.domain.academy.util.CategoryInfo.SCIENCE;
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
import org.guzzing.studayserver.domain.dashboard.facade.vo.AcademyInfo;
import org.guzzing.studayserver.domain.dashboard.facade.vo.ChildInfo;
import org.guzzing.studayserver.domain.dashboard.facade.vo.LessonInfo;
import org.guzzing.studayserver.domain.dashboard.model.Dashboard;
import org.guzzing.studayserver.domain.dashboard.model.DashboardSchedule;
import org.guzzing.studayserver.domain.dashboard.model.dto.PaymentInfo;
import org.guzzing.studayserver.domain.dashboard.model.vo.FeeInfo;
import org.guzzing.studayserver.domain.dashboard.model.vo.SimpleMemo;
import org.guzzing.studayserver.domain.dashboard.repository.DashboardRepository;
import org.guzzing.studayserver.domain.dashboard.service.dto.request.DashboardPostParam;
import org.guzzing.studayserver.domain.dashboard.service.dto.request.DashboardPutParam;
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
                List.of(new Schedule(6, "12:00", "21:04")),
                new PaymentInfo(1_000L, 2_000L, 3_000L, 4_000L, LocalDate.now()),
                new SimpleMemo(true, false, false, true, false, true));
    }

    public DashboardPostParam makePostParam() {
        return new DashboardPostParam(
                1L, 1L, 1L,
                new ScheduleInfos(List.of(
                        new ScheduleInfo(MONDAY, "14:00", "18:00", WEEKLY),
                        new ScheduleInfo(SUNDAY, "12:30", "12:04", WEEKLY))),
                new PaymentInfo(1_000L, 2_000L, 3_000L, 4_000L, LocalDate.now()),
                new SimpleMemo(true, false, true, true, false, true));
    }

    public DashboardPutRequest makePutRequest() {
        return new DashboardPutRequest(
                new PaymentInfo(4_000L, 4_000L, 4_000L, 4_000L, LocalDate.now()),
                new SimpleMemo(true, false, false, true, false, true));
    }

    public DashboardPutParam makePutParam(final long dashboardId) {
        return new DashboardPutParam(
                dashboardId,
                new PaymentInfo(4_000L, 4_000L, 4_000L, 4_000L, LocalDate.now()),
                new SimpleMemo(false, true, true, false, true, true));
    }

    public Dashboard makeEntity() {
        return new Dashboard(
                1L, 1L, 1L,
                List.of(new DashboardSchedule(MONDAY, "14:00", "18:00", WEEKLY),
                        new DashboardSchedule(FRIDAY, "13:30", "15:40", WEEKLY)),
                new FeeInfo(1_000L, 1_000L, 1_000L, 1_000L, LocalDate.of(LocalDate.now().getYear(), 4, 23)),
                Map.of(KINDNESS.getType(), false,
                        GOOD_FACILITY.getType(), false,
                        CHEAP_FEE.getType(), true,
                        GOOD_MANAGEMENT.getType(), true,
                        LOVELY_TEACHING.getType(), false,
                        SHUTTLE_AVAILABILITY.getType(), false),
                true, false);
    }

    public Dashboard makeNotActiveEntity() {
        return new Dashboard(
                1L, 1L, 1L,
                List.of(new DashboardSchedule(MONDAY, "14:00", "18:00", WEEKLY),
                        new DashboardSchedule(FRIDAY, "13:30", "15:40", WEEKLY)),
                new FeeInfo(1_000L, 1_000L, 1_000L, 1_000L, LocalDate.of(LocalDate.now().getYear(), 4, 23)),
                Map.of(KINDNESS.getType(), false,
                        GOOD_FACILITY.getType(), false,
                        CHEAP_FEE.getType(), true,
                        GOOD_MANAGEMENT.getType(), true,
                        LOVELY_TEACHING.getType(), false,
                        SHUTTLE_AVAILABILITY.getType(), false),
                false, false);
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
                List.of(SCIENCE.getCategoryName(), MATH.getCategoryName())
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
