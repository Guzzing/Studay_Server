package org.guzzing.studayserver.domain.child.service;

import static java.time.DayOfWeek.MONDAY;
import static java.time.DayOfWeek.SUNDAY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.guzzing.studayserver.domain.dashboard.model.vo.Repeatance.WEEKLY;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.given;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import org.guzzing.studayserver.domain.academy.model.Academy;
import org.guzzing.studayserver.domain.academy.model.Lesson;
import org.guzzing.studayserver.domain.academy.model.vo.Address;
import org.guzzing.studayserver.domain.academy.model.vo.Location;
import org.guzzing.studayserver.domain.academy.model.vo.academyinfo.AcademyInfo;
import org.guzzing.studayserver.domain.academy.model.vo.academyinfo.ShuttleAvailability;
import org.guzzing.studayserver.domain.academy.repository.academy.AcademyRepository;
import org.guzzing.studayserver.domain.academy.repository.lesson.LessonRepository;
import org.guzzing.studayserver.domain.academy.util.GeometryUtil;
import org.guzzing.studayserver.domain.calendar.model.Periodicity;
import org.guzzing.studayserver.domain.calendar.service.AcademyCalendarService;
import org.guzzing.studayserver.domain.calendar.service.dto.param.AcademyCalendarCreateParam;
import org.guzzing.studayserver.domain.calendar.service.dto.param.LessonScheduleParam;
import org.guzzing.studayserver.domain.child.service.param.ChildCreateParam;
import org.guzzing.studayserver.domain.dashboard.model.dto.PaymentInfo;
import org.guzzing.studayserver.domain.dashboard.model.vo.SimpleMemo;
import org.guzzing.studayserver.domain.dashboard.service.DashboardService;
import org.guzzing.studayserver.domain.dashboard.service.dto.request.DashboardPostParam;
import org.guzzing.studayserver.domain.dashboard.service.dto.response.DashboardResult;
import org.guzzing.studayserver.domain.dashboard.service.vo.ScheduleInfo;
import org.guzzing.studayserver.domain.dashboard.service.vo.ScheduleInfos;
import org.guzzing.studayserver.domain.member.model.Member;
import org.guzzing.studayserver.domain.member.model.NickName;
import org.guzzing.studayserver.domain.member.model.vo.MemberProvider;
import org.guzzing.studayserver.domain.member.model.vo.RoleType;
import org.guzzing.studayserver.domain.member.repository.MemberRepository;
import org.guzzing.studayserver.global.common.profile.ProfileImageUriProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
class ChildScheduleQueryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ChildService childService;

    @MockBean
    private ProfileImageUriProvider profileImageUriProvider;

    @Autowired
    private AcademyRepository academyRepository;

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private DashboardService dashboardService;

    @Autowired
    private AcademyCalendarService academyCalendarService;

    @Autowired
    private ChildScheduleQuery childScheduleQuery;

    @Test
    void findScheduleByMemberIdAndDate() {
        // given
        Member member = Member.of(new NickName("멤버 아이디"), "123", MemberProvider.KAKAO, RoleType.USER);
        Member savedMember = memberRepository.save(member);

        ChildCreateParam childCreateParam = new ChildCreateParam("아이 닉네임", "초등학교 1학년");
        given(profileImageUriProvider.provideDefaultProfileImageURI(anyList()))
                .willReturn("image.png");
        Long childId = childService.create(childCreateParam, savedMember.getId());

        Academy academy = Academy.of(
                (long) Objects.hash("수원", "유원우 코딩학원", "경기도 성남시 중원구 망포동", "유원우"),
                AcademyInfo.of("유원우 코딩학원", "000-0000-0000", ShuttleAvailability.AVAILABLE.name(), "예능(대)"),
                Address.of("경기도 성남시 중원구 망포동"),
                Location.of(37.4449168, 127.1388684));
        academy.changePoint(GeometryUtil.createPoint(37.4449168, 127.1388684));
        Academy savedAcademy = academyRepository.save(academy);

        Lesson lesson = Lesson.of(savedAcademy, "DB에 대해서", "인덱스란 뭘까", "20", "1개월", "100000");
        Lesson savedLesson = lessonRepository.save(lesson);

        DashboardPostParam dashboardPostParam = new DashboardPostParam(childId, savedLesson.getId(),
                savedLesson.getId(),
                new ScheduleInfos(List.of(
                        new ScheduleInfo(MONDAY, "14:00", "18:00", WEEKLY),
                        new ScheduleInfo(SUNDAY, "12:30", "12:04", WEEKLY))),
                new PaymentInfo(1_000L, 2_000L, 3_000L, 4_000L, LocalDate.now()),
                new SimpleMemo(true, false, true, true, false, true));
        DashboardResult savedDashboard = dashboardService.saveDashboard(dashboardPostParam);

        LessonScheduleParam mondayDashboardScheduleParam = new LessonScheduleParam(MONDAY, LocalTime.of(18, 0),
                LocalTime.of(20, 0));
        LessonScheduleParam fridayDashboardScheduleParam = new LessonScheduleParam(DayOfWeek.FRIDAY,
                LocalTime.of(18, 0),
                LocalTime.of(20, 0));
        AcademyCalendarCreateParam academyCalendarCreateParam = new AcademyCalendarCreateParam(
                List.of(mondayDashboardScheduleParam, fridayDashboardScheduleParam),
                LocalDate.of(2023, 11, 15),
                LocalDate.of(2024, 11, 15),
                false,
                childId,
                savedDashboard.dashboardId(),
                "매월 20일마다 상담 진행",
                Periodicity.WEEKLY
        );
        academyCalendarService.createSchedules(academyCalendarCreateParam);

        LocalDateTime fridayWithSchedule = LocalDateTime.of(2023, 11, 17, 18, 1);

        // when
        List<ChildDateScheduleResult> scheduleByMemberIdAndDate = childScheduleQuery.findScheduleByMemberIdAndDate(
                savedMember.getId(), fridayWithSchedule.toLocalDate(), fridayWithSchedule.toLocalTime());

        // then
        assertThat(scheduleByMemberIdAndDate).isNotEmpty();
    }
}
