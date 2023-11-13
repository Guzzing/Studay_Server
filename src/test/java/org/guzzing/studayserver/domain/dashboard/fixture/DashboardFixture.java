package org.guzzing.studayserver.domain.dashboard.fixture;

import static java.time.DayOfWeek.FRIDAY;
import static java.time.DayOfWeek.MONDAY;
import static java.time.DayOfWeek.SUNDAY;
import static org.guzzing.studayserver.domain.dashboard.model.vo.Repeatance.BIWEEKLY;
import static org.guzzing.studayserver.domain.dashboard.model.vo.Repeatance.DAILY;
import static org.guzzing.studayserver.domain.dashboard.model.vo.Repeatance.WEEKLY;
import static org.guzzing.studayserver.domain.dashboard.model.vo.SimpleMemoType.CHEAP_FEE;
import static org.guzzing.studayserver.domain.dashboard.model.vo.SimpleMemoType.LOVELY_TEACHING;

import java.time.LocalDate;
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
import org.guzzing.studayserver.domain.dashboard.repository.DashboardRepository;
import org.guzzing.studayserver.domain.dashboard.service.dto.request.DashboardParam;
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

    public DashboardParam makePostParam() {
        return new DashboardParam(
                1L, 1L, 1L,
                new ScheduleInfos(List.of(
                        new ScheduleInfo(null, "14:00", "18:00", DAILY),
                        new ScheduleInfo(SUNDAY, "12:30", "12:04", BIWEEKLY))),
                new PaymentInfo(1_000L, 2_000L, 3_000L, 4_000L, LocalDate.now()),
                Map.of(
                        SimpleMemoType.KINDNESS, true,
                        SimpleMemoType.GOOD_FACILITY, false,
                        SimpleMemoType.GOOD_MANAGEMENT, true,
                        CHEAP_FEE, true,
                        SimpleMemoType.LOVELY_TEACHING, false,
                        SimpleMemoType.SHUTTLE_AVAILABILITY, true));
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
        return new AcademyInfo(academyId, "메가스터디", "서울특별시 동작구 노량진동 메가스터디타워");
    }

    public LessonInfo makeLessonInfo() {
        return new LessonInfo(lessonId, "국어");
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
