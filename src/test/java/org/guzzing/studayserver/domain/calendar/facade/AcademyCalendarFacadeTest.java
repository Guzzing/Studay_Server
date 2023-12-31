package org.guzzing.studayserver.domain.calendar.facade;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

import java.util.List;
import org.guzzing.studayserver.domain.academy.model.Academy;
import org.guzzing.studayserver.domain.academy.model.Lesson;
import org.guzzing.studayserver.domain.academy.repository.academy.AcademyRepository;
import org.guzzing.studayserver.domain.academy.repository.lesson.LessonRepository;
import org.guzzing.studayserver.domain.calendar.facade.dto.AcademyCalendarDetailFacadeParam;
import org.guzzing.studayserver.domain.calendar.facade.dto.AcademyCalendarDetailFacadeResult;
import org.guzzing.studayserver.domain.calendar.facade.dto.AcademyScheduleLoadToUpdateFacadeResult;
import org.guzzing.studayserver.domain.calendar.model.AcademySchedule;
import org.guzzing.studayserver.domain.calendar.model.AcademyTimeTemplate;
import org.guzzing.studayserver.domain.calendar.repository.academyschedule.AcademyScheduleRepository;
import org.guzzing.studayserver.domain.calendar.repository.academytimetemplate.AcademyTimeTemplateRepository;
import org.guzzing.studayserver.domain.calendar.service.AcademyCalendarService;
import org.guzzing.studayserver.domain.calendar.service.dto.param.AcademyCalendarCreateParam;
import org.guzzing.studayserver.domain.calendar.service.dto.result.AcademyCalendarCreateResults;
import org.guzzing.studayserver.domain.child.model.Child;
import org.guzzing.studayserver.domain.child.repository.ChildRepository;
import org.guzzing.studayserver.domain.dashboard.model.Dashboard;
import org.guzzing.studayserver.domain.dashboard.repository.DashboardRepository;
import org.guzzing.studayserver.domain.member.model.Member;
import org.guzzing.studayserver.domain.member.repository.MemberRepository;
import org.guzzing.studayserver.testutil.fixture.academy.AcademyFixture;
import org.guzzing.studayserver.testutil.fixture.academycalender.AcademyCalenderFixture;
import org.guzzing.studayserver.testutil.fixture.child.ChildFixture;
import org.guzzing.studayserver.testutil.fixture.dashboard.DashboardFixture;
import org.guzzing.studayserver.testutil.fixture.member.MemberFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest(webEnvironment = NONE)
class AcademyCalendarFacadeTest {

    @Autowired
    private DashboardRepository dashboardRepository;

    @Autowired
    private DashboardFixture dashboardFixture;

    @Autowired
    private AcademyTimeTemplateRepository academyTimeTemplateRepository;

    @Autowired
    private AcademyScheduleRepository academyScheduleRepository;

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private AcademyRepository academyRepository;

    @Autowired
    private ChildRepository childRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private AcademyCalendarFacade academyCalendarFacade;

    @Autowired
    private AcademyCalendarService academyCalendarService;

    private Long timeTemplateId;
    private Long scheduleId;
    private AcademyTimeTemplate academyTimeTemplate;
    private AcademySchedule academySchedule;
    private List<AcademySchedule> academySchedules;

    @BeforeEach
    void setUp() {
        AcademyCalendarCreateParam academyCalendarCreateParam =
                AcademyCalenderFixture.firstChildAcademyCalenderCreateParam();
        AcademyCalendarCreateResults createdTimeTemplates = academyCalendarService.createSchedules(
                academyCalendarCreateParam);

        timeTemplateId = createdTimeTemplates.academyTimeTemplateIds().get(0);
        academyTimeTemplate = academyTimeTemplateRepository.getById(timeTemplateId);
        academySchedules = academyScheduleRepository.findByAcademyTimeTemplateId(timeTemplateId);
        academySchedule = academySchedules.get(0);
        scheduleId = academySchedule.getId();
    }

    @Test
    @DisplayName("스케줄 업데이트 전 기존 글을 불러올 때 해당 스케줄에 대한 정보와 대시보드에 대한 정보가 올바르게 반환되는지 확인한다.")
    void loadTimeTemplateToUpdate_existedScheduleInfo_success() {
        //Given
        Dashboard savedDashboard = dashboardRepository.save(dashboardFixture.makeEntity());

        //When
        AcademyScheduleLoadToUpdateFacadeResult academyScheduleLoadToUpdateFacadeResult
                = academyCalendarFacade.loadTimeTemplateToUpdate(scheduleId);

        //Then
        assertAll("AcademySchedule Load To Update",
                () -> assertThat(academyScheduleLoadToUpdateFacadeResult.academyId()).isEqualTo(
                        savedDashboard.getAcademyId()),
                () -> assertThat(academyScheduleLoadToUpdateFacadeResult.childId()).isEqualTo(
                        savedDashboard.getChildId()),
                () -> assertThat(academyScheduleLoadToUpdateFacadeResult.dashboardId()).isEqualTo(
                        savedDashboard.getId()),
                () -> assertThat(academyScheduleLoadToUpdateFacadeResult.lessonId()).isEqualTo(
                        savedDashboard.getLessonId()),
                () -> assertThat(academyScheduleLoadToUpdateFacadeResult.endDateOfAttendance()).isEqualTo(
                        academyTimeTemplate.getEndDateOfAttendance()),
                () -> assertThat(academyScheduleLoadToUpdateFacadeResult.startDateOfAttendance()).isEqualTo(
                        academyTimeTemplate.getStartDateOfAttendance()),
                () -> assertThat(academyScheduleLoadToUpdateFacadeResult.memo()).isEqualTo(
                        academyTimeTemplate.getMemo())
        );
    }

    @Test
    @DisplayName("스케줄 상세보기시 아이에 대한 정보와 학원 수업에 대한 정보를 올바르게 반환하는지 확인한다.")
    void getCalendarDetailInfo_academyAndChildInfoAndSchedule_success() {
        //Given
        String imageUrlPrefix = "https://team09-resources-bucket.s3.ap-northeast-1.amazonaws.com/";

        Academy academyAboutSungnam = AcademyFixture.academySungnam();
        academyAboutSungnam.changeEducationFee(100000L);
        Academy savedAcademyAboutSungnam = academyRepository.save(academyAboutSungnam);
        Lesson lessonAboutSungnam = AcademyFixture.lessonForSunganm(savedAcademyAboutSungnam);
        Lesson savedALessonAboutSungnam = lessonRepository.save(lessonAboutSungnam);

        Member savedMember = memberRepository.save(MemberFixture.makeMemberEntity());
        Child child = ChildFixture.child();
        child.assignToNewMemberOnly(savedMember);
        Child savedChild = childRepository.save(child);

        AcademyCalendarDetailFacadeParam academyCalendarDetailFacadeParam
                = new AcademyCalendarDetailFacadeParam(
                savedALessonAboutSungnam.getId(),
                savedChild.getId(),
                scheduleId);

        //When
        AcademyCalendarDetailFacadeResult calendarDetailInfo
                = academyCalendarFacade.getCalendarDetailInfo(academyCalendarDetailFacadeParam);

        //Then
        assertAll("AcademyCalendar Detail",
                () -> assertThat(calendarDetailInfo.childrenInfo().childId()).isEqualTo(savedChild.getId()),
                () -> assertThat(calendarDetailInfo.childrenInfo().memo()).isEqualTo(academyTimeTemplate.getMemo()),
                () -> assertThat(calendarDetailInfo.childrenInfo().childName()).isEqualTo(savedChild.getNickName()),
                () -> assertThat(calendarDetailInfo.childrenInfo().imageUrl()).isEqualTo(
                        imageUrlPrefix + savedChild.getProfileImageURIPath()),
                () -> assertThat(calendarDetailInfo.childrenInfo().dashBoardId()).isEqualTo(
                        academyTimeTemplate.getDashboardId()),
                () -> assertThat(calendarDetailInfo.lessonInfo().lessonName()).isEqualTo(
                        savedALessonAboutSungnam.getCurriculum()),
                () -> assertThat(calendarDetailInfo.lessonInfo().lessonTimes().endTime()).isEqualTo(
                        academySchedule.getLessonEndTime().toString()),
                () -> assertThat(calendarDetailInfo.lessonInfo().lessonTimes().startTime()).isEqualTo(
                        academySchedule.getLessonStartTime().toString()),
                () -> assertThat(calendarDetailInfo.lessonInfo().capacity()).isEqualTo(
                        savedALessonAboutSungnam.getCapacity()),
                () -> assertThat(calendarDetailInfo.lessonInfo().totalFee()).isEqualTo(
                        savedALessonAboutSungnam.getTotalFee()),
                () -> assertThat(calendarDetailInfo.academyInfoAboutScheduleDetail().academyName()).isEqualTo(
                        savedAcademyAboutSungnam.getAcademyName()),
                () -> assertThat(calendarDetailInfo.academyInfoAboutScheduleDetail().address()).isEqualTo(
                        savedAcademyAboutSungnam.getFullAddress())
        );
    }

}
