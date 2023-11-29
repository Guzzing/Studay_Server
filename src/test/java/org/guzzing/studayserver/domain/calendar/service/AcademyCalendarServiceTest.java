package org.guzzing.studayserver.domain.calendar.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;
import org.guzzing.studayserver.domain.calendar.exception.DateOverlapException;
import org.guzzing.studayserver.domain.calendar.model.AcademySchedule;
import org.guzzing.studayserver.domain.calendar.model.AcademyTimeTemplate;
import org.guzzing.studayserver.domain.calendar.repository.academyschedule.AcademyScheduleRepository;
import org.guzzing.studayserver.domain.calendar.repository.academytimetemplate.AcademyTimeTemplateRepository;
import org.guzzing.studayserver.domain.calendar.service.dto.RepeatPeriod;
import org.guzzing.studayserver.domain.calendar.service.dto.param.AcademyCalendarCreateParam;
import org.guzzing.studayserver.domain.calendar.service.dto.param.AcademyCalendarDeleteByDashboardParam;
import org.guzzing.studayserver.domain.calendar.service.dto.param.AcademyCalendarDeleteParam;
import org.guzzing.studayserver.domain.calendar.service.dto.param.AcademyCalendarDetailParam;
import org.guzzing.studayserver.domain.calendar.service.dto.param.AcademyCalendarUpdateParam;
import org.guzzing.studayserver.domain.calendar.service.dto.result.AcademyCalendarCreateResults;
import org.guzzing.studayserver.domain.calendar.service.dto.result.AcademyCalendarDetailResult;
import org.guzzing.studayserver.domain.calendar.service.dto.result.AcademyCalendarLoadToUpdateResult;
import org.guzzing.studayserver.domain.dashboard.service.access.DashboardAccessService;
import org.guzzing.studayserver.domain.dashboard.service.access.dto.DashboardScheduleAccessResult;
import org.guzzing.studayserver.testutil.fixture.academycalender.AcademyCalenderFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest(webEnvironment = NONE)
class AcademyCalendarServiceTest {
    @Autowired
    private AcademyCalendarService academyCalendarService;

    @Autowired
    private AcademyScheduleRepository academyScheduleRepository;

    @Autowired
    private AcademyTimeTemplateRepository academyTimeTemplateRepository;

    @Autowired
    private PeriodicStrategy periodicStrategy;

    @MockBean
    private DashboardAccessService dashboardAccessService;

    @Test
    @DisplayName("대시보드를 통해 가져온 학원 일정 정보를 바탕으로 학원 스케줄을 생성하고 그 범위 이내에 올바르게 생성되는지 확인한다.")
    void createSchedules_success() {
        //Given
        AcademyCalendarCreateParam academyCalendarCreateParam =
                AcademyCalenderFixture.firstChildAcademyCalenderCreateParam();
        RepeatPeriod fridayRepeatPeriod = AcademyCalenderFixture.fridayRepeatPeriod();
        RepeatPeriod mondayRepeatPeriod = AcademyCalenderFixture.mondayRepeatPeriod();

        //When
        AcademyCalendarCreateResults academyCalendarCreateResults =
                academyCalendarService.createSchedules(academyCalendarCreateParam);
        List<LocalDate> schedules =
                Stream.concat(
                        periodicStrategy.createSchedules(fridayRepeatPeriod).stream(),
                        periodicStrategy.createSchedules(mondayRepeatPeriod).stream()
                ).toList();
        List<AcademySchedule> academySchedules = academyScheduleRepository.findAll();

        //Then
        assertThat(academyCalendarCreateResults.academyTimeTemplateIds().size()).isEqualTo(
                academyCalendarCreateParam.lessonScheduleParams().size());
        assertThat(academySchedules.size()).isEqualTo(schedules.size());
        academySchedules.forEach(academySchedule -> {
            assertThat(schedules).contains(academySchedule.getScheduleDate());
        });
    }

    @Test
    @DisplayName("기존에 존재하는 학원 스케줄 일정과 중복된 일정을 생성하려고 하는 경우 예외를 던진다.")
    void createOverlapSchedules_throwException() {
        //Given
        academyTimeTemplateRepository.save(AcademyCalenderFixture.overlapAcademyTimeTemplate());
        AcademyCalendarCreateParam academyCalendarCreateParam
                = AcademyCalenderFixture.firstChildAcademyCalenderCreateParam();

        //When & Then
        assertThatThrownBy(
                () -> academyCalendarService.createSchedules(academyCalendarCreateParam)
        ).isInstanceOf(DateOverlapException.class);
    }

    @Test
    @DisplayName("하나의 학원 스케줄을 수정하려고 할 때 기존의 정보를 올바르게 불러오는지 대시보드 데이터와 비교한다.")
    void loadTimeTemplateToUpdate() {
        //Given
        AcademyTimeTemplate savedMondayAcademyTimeTemplate =
                academyTimeTemplateRepository.save(
                        AcademyCalenderFixture.mondayAcademyTimeTemplate()
                );
        AcademyTimeTemplate savedFridayAcademyTimeTemplate =
                academyTimeTemplateRepository.save(
                        AcademyCalenderFixture.fridayAcademyTimeTemplate()
                );
        AcademySchedule savedMondayAcademySchedule =
                academyScheduleRepository.save(
                        AcademyCalenderFixture.mondayAcademySchedule(savedMondayAcademyTimeTemplate)
                );
        AcademySchedule savedFridayAcademySchedule =
                academyScheduleRepository.save(
                        AcademyCalenderFixture.fridayAcademySchedule(savedFridayAcademyTimeTemplate)
                );

        Long academyScheduleId = savedFridayAcademySchedule.getId();
        DashboardScheduleAccessResult mockDashboardScheduleAccessResult = AcademyCalenderFixture.dashboardScheduleAccessResult();

        given(dashboardAccessService.getDashboardSchedule(savedFridayAcademyTimeTemplate.getDashboardId())).willReturn(
                mockDashboardScheduleAccessResult);

        //When
        AcademyCalendarLoadToUpdateResult academyCalendarLoadToUpdateResult = academyCalendarService.loadTimeTemplateToUpdate(
                academyScheduleId);

        //Then
        assertThat(academyCalendarLoadToUpdateResult.academyName()).isEqualTo(
                mockDashboardScheduleAccessResult.academyName());
        assertThat(academyCalendarLoadToUpdateResult.lessonName()).isEqualTo(
                mockDashboardScheduleAccessResult.lessonName());
        assertThat(academyCalendarLoadToUpdateResult.startDateOfAttendance()).isEqualTo(
                savedFridayAcademyTimeTemplate.getStartDateOfAttendance());
        assertThat(academyCalendarLoadToUpdateResult.startDateOfAttendance()).isEqualTo(
                savedMondayAcademyTimeTemplate.getStartDateOfAttendance());
        assertThat(academyCalendarLoadToUpdateResult.endDateOfAttendance()).isEqualTo(
                savedFridayAcademyTimeTemplate.getEndDateOfAttendance());
        assertThat(academyCalendarLoadToUpdateResult.endDateOfAttendance()).isEqualTo(
                savedMondayAcademyTimeTemplate.getEndDateOfAttendance());
        assertThat(academyCalendarLoadToUpdateResult.memo()).isEqualTo(savedFridayAcademyTimeTemplate.getMemo());
        assertThat(academyCalendarLoadToUpdateResult.memo()).isEqualTo(savedMondayAcademyTimeTemplate.getMemo());
        assertThat(academyCalendarLoadToUpdateResult.isAlarmed()).isEqualTo(savedFridayAcademyTimeTemplate.isAlarmed());
        assertThat(academyCalendarLoadToUpdateResult.isAlarmed()).isEqualTo(savedMondayAcademyTimeTemplate.isAlarmed());

        academyCalendarLoadToUpdateResult.lessonScheduleAccessResults()
                .forEach(lessonScheduleAccessResult -> {
                    if (lessonScheduleAccessResult.dayOfWeek() == DayOfWeek.MONDAY) {
                        assertThat(lessonScheduleAccessResult.lessonEndTime()).isEqualTo(
                                savedMondayAcademySchedule.getLessonEndTime());
                        assertThat(lessonScheduleAccessResult.lessonStartTime()).isEqualTo(
                                savedMondayAcademySchedule.getLessonStartTime());
                    }

                    if (lessonScheduleAccessResult.dayOfWeek() == DayOfWeek.FRIDAY) {
                        assertThat(lessonScheduleAccessResult.lessonEndTime()).isEqualTo(
                                savedFridayAcademySchedule.getLessonEndTime());
                        assertThat(lessonScheduleAccessResult.lessonStartTime()).isEqualTo(
                                savedFridayAcademySchedule.getLessonStartTime());
                    }
                });
    }

    @Test
    @DisplayName("모든 일정에 대해서 일괄 수정을 진행한 결과가 의도한 일정 범위 내에 잘 만들어졌는지 확인한다.")
    void updateTimeTemplate_isAllUpdated() {
        //Given
        AcademyCalendarCreateParam academyCalendarCreateParam =
                AcademyCalenderFixture.firstChildAcademyCalenderCreateParam();

        academyCalendarService.createSchedules(academyCalendarCreateParam);
        AcademyCalendarUpdateParam academyCalendarUpdateParam =
                AcademyCalenderFixture.isAllUpdatedAcademyCalendarUpdateParam();

        //When
        academyCalendarService.updateTimeTemplate(academyCalendarUpdateParam);
        RepeatPeriod allUpdatedFridayRepeatPeriod = AcademyCalenderFixture.isAllUpdatedFridayRepeatPeriod();
        RepeatPeriod allUpdatedMondayRepeatPeriod = AcademyCalenderFixture.isAllUpdatedMondayRepeatPeriod();
        List<LocalDate> schedules = Stream.concat(
                        periodicStrategy.createSchedules(allUpdatedFridayRepeatPeriod).stream(),
                        periodicStrategy.createSchedules(allUpdatedMondayRepeatPeriod).stream())
                .toList();
        List<AcademySchedule> allAcademySchedules = academyScheduleRepository.findAll();

        //Then
        assertThat(allAcademySchedules.size()).isEqualTo(schedules.size());
        allAcademySchedules.forEach(academySchedule -> {
            assertThat(schedules).contains(academySchedule.getScheduleDate());
        });
    }

    @Test
    @DisplayName("해당 일 이후 수정된 스케줄 개수와 수정되지 않고 남은 스케줄의 개수가 전체 스케줄의 개수와 일치하는지 확인한다.")
    void updateTimeTemplate_afterUpdated() {
        //Given
        AcademyCalendarCreateParam academyCalendarCreateParam
                = AcademyCalenderFixture.firstChildAcademyCalenderCreateParam();

        academyCalendarService.createSchedules(academyCalendarCreateParam);
        AcademyCalendarUpdateParam academyCalendarUpdateParam =
                AcademyCalenderFixture.isNotAllUpdatedAcademyCalendarUpdateParam();

        //When
        academyCalendarService.updateTimeTemplate(academyCalendarUpdateParam);
        RepeatPeriod notAllUpdatedFridayRepeatPeriod = AcademyCalenderFixture.isNotAllUpdatedFridayRepeatPeriod();
        RepeatPeriod notAllUpdatedMondayRepeatPeriod = AcademyCalenderFixture.isNotAllUpdatedMondayRepeatPeriod();
        RepeatPeriod existedFridayRepeatPeriod = AcademyCalenderFixture.isExistedFridayRepeatPeriod();
        RepeatPeriod existeddMondayRepeatPeriod = AcademyCalenderFixture.isExisteddMondayRepeatPeriod();

        List<LocalDate> afterUpdatedSchedules = Stream.concat(
                        periodicStrategy.createSchedules(notAllUpdatedFridayRepeatPeriod).stream(),
                        periodicStrategy.createSchedules(notAllUpdatedMondayRepeatPeriod).stream())
                .toList();
        List<LocalDate> existedSchedules = Stream.concat(
                        periodicStrategy.createSchedules(existedFridayRepeatPeriod).stream(),
                        periodicStrategy.createSchedules(existeddMondayRepeatPeriod).stream())
                .toList();
        List<AcademySchedule> allAcademySchedules = academyScheduleRepository.findAll();

        //Then
        assertThat(allAcademySchedules.size()).isEqualTo(afterUpdatedSchedules.size() + existedSchedules.size());
    }

    @Test
    @DisplayName("해당 일자의 스케줄만 삭제하였을 때 기존에 남은 스케줄 개수에서 하나만 줄어들었는지 확인한다.")
    void deletedSchedule_isOnlyThatSchedule() {
        //Given
        AcademyCalendarCreateParam academyCalendarCreateParam
                = AcademyCalenderFixture.firstChildAcademyCalenderCreateParam();

        AcademyCalendarCreateResults academyCalendarCreateResults = academyCalendarService.createSchedules(
                academyCalendarCreateParam);
        Long timeTemplateId = academyCalendarCreateResults.academyTimeTemplateIds().get(0);

        List<AcademySchedule> academySchedules = academyScheduleRepository.findByAcademyTimeTemplateId(timeTemplateId);
        Long scheduleId = academySchedules.get(0).getId();
        AcademyCalendarDeleteParam academyCalendarDeleteParam =
                AcademyCalenderFixture.isOnlyThatAcademyCalendarDeleteParam(scheduleId);
        List<AcademySchedule> allAcademySchedules = academyScheduleRepository.findAll();

        //When
        academyCalendarService.deleteSchedule(academyCalendarDeleteParam);
        List<AcademySchedule> isOnlyDeletedScheduleAcademySchedules = academyScheduleRepository.findAll();

        //Then
        assertThat(isOnlyDeletedScheduleAcademySchedules.size()).isEqualTo(allAcademySchedules.size() - 1);
    }

    @Test
    @DisplayName("해당 일 이후 삭제하는 경우 남은 스케줄의 일정이 해당일 이전으로 올바르게 남아있는지 확인한다.")
    void deletedSchedule_isAfterSchedule() {
        //Given
        AcademyCalendarCreateParam academyCalendarCreateParam =
                AcademyCalenderFixture.firstChildAcademyCalenderCreateParam();
        AcademyCalendarCreateResults academyCalendarCreateResults = academyCalendarService.createSchedules(
                academyCalendarCreateParam);

        Long timeTemplateId = academyCalendarCreateResults.academyTimeTemplateIds().get(0);

        List<AcademySchedule> academySchedules = academyScheduleRepository.findByAcademyTimeTemplateId(timeTemplateId);
        Long scheduleId = academySchedules.get(0).getId();

        AcademyCalendarDeleteParam academyCalendarDeleteParam =
                AcademyCalenderFixture.isAfterAcademyCalendarDeleteParam(scheduleId);


        academyCalendarService.deleteSchedule(academyCalendarDeleteParam);
        List<AcademySchedule> isAfterDeletedScheduleAcademySchedules = academyScheduleRepository.findAll();

        //Then
        isAfterDeletedScheduleAcademySchedules.forEach(
                schedule -> assertThat(
                        schedule.getScheduleDate().isBefore(academySchedules.get(0).getScheduleDate())).isTrue()
        );
    }

    @Test
    @DisplayName("대시보드에 삭제를 요청하는 경우  남은 스케줄의 일정이 요청일 이전으로 올바르게 남아있는지 확인한다.")
    void deletedScheduleByDashboard_isAfterSchedule() {
        //Given
        AcademyCalendarCreateParam academyCalendarCreateParam
                = AcademyCalenderFixture.firstChildAcademyCalenderCreateParam();
        academyCalendarService.createSchedules(academyCalendarCreateParam);

        AcademyCalendarDeleteByDashboardParam param
                = AcademyCalenderFixture.academyCalendarDeleteByDashboardParam();

        //When
        academyCalendarService.deleteSchedulesByDashboard(param);
        List<AcademySchedule> isAfterDeletedScheduleAcademySchedules = academyScheduleRepository.findAll();

        //Then
        isAfterDeletedScheduleAcademySchedules.forEach(
                schedule -> assertThat(schedule.getScheduleDate().isBefore(param.requestedDate())).isTrue()
        );
    }

    @Test
    @DisplayName("두 아이 학원 수업의 시작이 시간이 같은 어떤 특정 날짜 스케줄의 상세보기를 열면 " +
            "아이 각각에 대한 메모, 대시보드를 알 수 있고 공통의 수업 시작시간과 끝나는 시간을 알 수 있다.")
    void detailSchedules() {
        //Given
        LocalDate detailRequestedDate = LocalDate.of(2023, 11, 20);

        AcademyCalendarCreateParam firstChildAcademyCalendarCreateParam
                = AcademyCalenderFixture.firstChildAcademyCalenderCreateParam();
        AcademyCalendarCreateResults firstChildTimeTemplates = academyCalendarService.createSchedules(
                firstChildAcademyCalendarCreateParam);

        AcademyTimeTemplate firstChildMondayTimeTemplate = firstChildTimeTemplates.academyTimeTemplateIds()
                .stream()
                .filter(id -> academyTimeTemplateRepository.getById(id).getDayOfWeek() == DayOfWeek.MONDAY)
                .map(id -> academyTimeTemplateRepository.getById(id))
                .findFirst()
                .get();

        List<AcademySchedule> firstChildSchedules = academyScheduleRepository.findByAcademyTimeTemplateId(
                firstChildMondayTimeTemplate.getId());

        Long firstChildScheduleId = firstChildSchedules.stream()
                .filter(academySchedule -> academySchedule.getScheduleDate().equals(detailRequestedDate))
                .map(academySchedule -> academySchedule.getId())
                .findFirst()
                .get();

        AcademyCalendarDetailParam academyCalendarDetailParam = new AcademyCalendarDetailParam(
               firstChildAcademyCalendarCreateParam.childId(),firstChildScheduleId);

        //When
        AcademyCalendarDetailResult academyCalendarDetailResult = academyCalendarService.detailSchedules(
                academyCalendarDetailParam);

        //Then
        assertThat(academyCalendarDetailResult.dashboardId()).isEqualTo(
                firstChildMondayTimeTemplate.getDashboardId());
        assertThat(academyCalendarDetailResult.memo()).isEqualTo(firstChildMondayTimeTemplate.getMemo());
        assertThat(academyCalendarDetailResult.lessonStartTime()).isEqualTo(
                firstChildSchedules.get(0).getLessonStartTime());
        assertThat(academyCalendarDetailResult.lessonEndTime()).isEqualTo(
                firstChildSchedules.get(0).getLessonEndTime());
    }

}
