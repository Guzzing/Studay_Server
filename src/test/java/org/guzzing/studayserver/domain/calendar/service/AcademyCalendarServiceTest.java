package org.guzzing.studayserver.domain.calendar.service;

import org.guzzing.studayserver.domain.calendar.exception.DateOverlapException;
import org.guzzing.studayserver.domain.calendar.model.AcademySchedule;
import org.guzzing.studayserver.domain.calendar.model.AcademyTimeTemplate;
import org.guzzing.studayserver.domain.calendar.repository.academyschedule.AcademyScheduleRepository;
import org.guzzing.studayserver.domain.calendar.repository.academytimetemplate.AcademyTimeTemplateRepository;
import org.guzzing.studayserver.domain.calendar.service.dto.RepeatPeriod;
import org.guzzing.studayserver.domain.calendar.service.dto.param.AcademyCalendarCreateParam;
import org.guzzing.studayserver.domain.calendar.service.dto.param.AcademyCalendarDeleteParam;
import org.guzzing.studayserver.domain.calendar.service.dto.param.AcademyCalendarUpdateParam;
import org.guzzing.studayserver.domain.calendar.service.dto.result.AcademyCalendarCreateResults;
import org.guzzing.studayserver.domain.calendar.service.dto.result.AcademyCalendarLoadToUpdateResult;
import org.guzzing.studayserver.domain.dashboard.DashboardAccessService;
import org.guzzing.studayserver.domain.dashboard.DashboardScheduleAccessResult;
import org.guzzing.studayserver.testutil.fixture.academycalender.AcademyCalenderFixture;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

@Transactional
@SpringBootTest(webEnvironment = NONE)
@ActiveProfiles({"dev", "oauth"})
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
    void createSchedules() {
        //Given
        AcademyCalendarCreateParam academyCalendarCreateParam = AcademyCalenderFixture.academyCalenderCreateParam();
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
        assertThat(academyCalendarCreateResults.academyTimeTemplateIds().size()).isEqualTo(academyCalendarCreateParam.lessonScheduleParams().size());
        assertThat(academySchedules.size()).isEqualTo(schedules.size());
        academySchedules.forEach(academySchedule -> {
            assertThat(schedules).contains(academySchedule.getScheduleDate());
        });
    }

    @Test
    void createOverlapSchedules() {
        //Given
        academyTimeTemplateRepository.save(AcademyCalenderFixture.overlapAcademyTimeTemplate());

        AcademyCalendarCreateParam academyCalendarCreateParam = AcademyCalenderFixture.academyCalenderCreateParam();

        //When & Then
        assertThatThrownBy(
                () -> academyCalendarService.createSchedules(academyCalendarCreateParam)
        ).isInstanceOf(DateOverlapException.class);
    }

    @Test
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

        given(dashboardAccessService.getDashboardSchedule(savedFridayAcademyTimeTemplate.getDashboardId())).willReturn(mockDashboardScheduleAccessResult);

        //When
        AcademyCalendarLoadToUpdateResult academyCalendarLoadToUpdateResult = academyCalendarService.loadTimeTemplateToUpdate(academyScheduleId);

        //Then
        assertThat(academyCalendarLoadToUpdateResult.academyName()).isEqualTo(mockDashboardScheduleAccessResult.academyName());
        assertThat(academyCalendarLoadToUpdateResult.lessonName()).isEqualTo(mockDashboardScheduleAccessResult.lessonName());
        assertThat(academyCalendarLoadToUpdateResult.startDateOfAttendance()).isEqualTo(savedFridayAcademyTimeTemplate.getStartDateOfAttendance());
        assertThat(academyCalendarLoadToUpdateResult.startDateOfAttendance()).isEqualTo(savedMondayAcademyTimeTemplate.getStartDateOfAttendance());
        assertThat(academyCalendarLoadToUpdateResult.endDateOfAttendance()).isEqualTo(savedFridayAcademyTimeTemplate.getEndDateOfAttendance());
        assertThat(academyCalendarLoadToUpdateResult.endDateOfAttendance()).isEqualTo(savedMondayAcademyTimeTemplate.getEndDateOfAttendance());
        assertThat(academyCalendarLoadToUpdateResult.memo()).isEqualTo(savedFridayAcademyTimeTemplate.getMemo());
        assertThat(academyCalendarLoadToUpdateResult.memo()).isEqualTo(savedMondayAcademyTimeTemplate.getMemo());
        assertThat(academyCalendarLoadToUpdateResult.isAlarmed()).isEqualTo(savedFridayAcademyTimeTemplate.isAlarmed());
        assertThat(academyCalendarLoadToUpdateResult.isAlarmed()).isEqualTo(savedMondayAcademyTimeTemplate.isAlarmed());

        academyCalendarLoadToUpdateResult.lessonScheduleAccessResults()
                .forEach(lessonScheduleAccessResult -> {
                    if (lessonScheduleAccessResult.dayOfWeek() == DayOfWeek.MONDAY) {
                        assertThat(lessonScheduleAccessResult.lessonEndTime()).isEqualTo(savedMondayAcademySchedule.getLessonEndTime());
                        assertThat(lessonScheduleAccessResult.lessonStartTime()).isEqualTo(savedMondayAcademySchedule.getLessonStartTime());
                    }

                    if (lessonScheduleAccessResult.dayOfWeek() == DayOfWeek.FRIDAY) {
                        assertThat(lessonScheduleAccessResult.lessonEndTime()).isEqualTo(savedFridayAcademySchedule.getLessonEndTime());
                        assertThat(lessonScheduleAccessResult.lessonStartTime()).isEqualTo(savedFridayAcademySchedule.getLessonStartTime());
                    }
                });
    }

    @Test
    void updateTimeTemplate_isAllUpdated() {
        //Given
        AcademyCalendarCreateParam academyCalendarCreateParam = AcademyCalenderFixture.academyCalenderCreateParam();

        academyCalendarService.createSchedules(academyCalendarCreateParam);
        AcademyCalendarUpdateParam academyCalendarUpdateParam =
                AcademyCalenderFixture.isAllUpdatedAcademyCalendarUpdateParam(academyCalendarCreateParam.dashboardId());

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
    void updateTimeTemplate_afterUpdated() {
        //Given
        AcademyCalendarCreateParam academyCalendarCreateParam = AcademyCalenderFixture.academyCalenderCreateParam();
        academyCalendarService.createSchedules(academyCalendarCreateParam);
        AcademyCalendarUpdateParam academyCalendarUpdateParam =
                AcademyCalenderFixture.isNotAllUpdatedAcademyCalendarUpdateParam(academyCalendarCreateParam.dashboardId());

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
    void deletedSchedule_isOnlyThatSchedule() {
        //Given
        AcademyCalendarCreateParam academyCalendarCreateParam = AcademyCalenderFixture.academyCalenderCreateParam();
        AcademyCalendarCreateResults academyCalendarCreateResults = academyCalendarService.createSchedules(academyCalendarCreateParam);
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
        assertThat(isOnlyDeletedScheduleAcademySchedules.size()).isEqualTo(allAcademySchedules.size()-1);
    }

    @Test
    void deletedSchedule_isAfterSchedule() {
        //Given
        AcademyCalendarCreateParam academyCalendarCreateParam = AcademyCalenderFixture.academyCalenderCreateParam();
        AcademyCalendarCreateResults academyCalendarCreateResults = academyCalendarService.createSchedules(academyCalendarCreateParam);
        Long timeTemplateId = academyCalendarCreateResults.academyTimeTemplateIds().get(0);

        List<AcademySchedule> academySchedules = academyScheduleRepository.findByAcademyTimeTemplateId(timeTemplateId);
        Long scheduleId = academySchedules.get(0).getId();
        AcademyCalendarDeleteParam academyCalendarDeleteParam =
                AcademyCalenderFixture.isAfterAcademyCalendarDeleteParam(scheduleId);

        //When
        academyCalendarService.deleteSchedule(academyCalendarDeleteParam);
        List<AcademySchedule> isOnlyDeletedScheduleAcademySchedules = academyScheduleRepository.findAll();

        //Then
        isOnlyDeletedScheduleAcademySchedules.forEach(
                schedule-> assertThat(schedule.getScheduleDate().isBefore(academyCalendarDeleteParam.requestDate())).isTrue()
        );
    }

}
