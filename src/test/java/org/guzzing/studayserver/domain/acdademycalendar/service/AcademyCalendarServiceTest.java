package org.guzzing.studayserver.domain.acdademycalendar.service;

import org.guzzing.studayserver.domain.acdademycalendar.model.AcademySchedule;
import org.guzzing.studayserver.domain.acdademycalendar.model.AcademyTimeTemplate;
import org.guzzing.studayserver.domain.acdademycalendar.repository.academyschedule.AcademyScheduleRepository;
import org.guzzing.studayserver.domain.acdademycalendar.repository.academytimetemplate.AcademyTimeTemplateRepository;
import org.guzzing.studayserver.domain.acdademycalendar.service.dto.RepeatPeriod;
import org.guzzing.studayserver.domain.acdademycalendar.service.dto.param.AcademyCalendarCreateParam;
import org.guzzing.studayserver.domain.acdademycalendar.service.dto.param.AcademyCalendarLoadToUpdateParam;
import org.guzzing.studayserver.domain.acdademycalendar.service.dto.result.AcademyCalendarCreateResults;
import org.guzzing.studayserver.domain.acdademycalendar.service.dto.result.AcademyCalendarLoadToUpdateResult;
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
        AcademyCalendarCreateResults academyCalendarCreateResults = academyCalendarService.createSchedules(academyCalendarCreateParam);
        List<LocalDate> schedules = Stream.concat(periodicStrategy.createSchedules(fridayRepeatPeriod).stream(), periodicStrategy.createSchedules(mondayRepeatPeriod).stream())
                .toList();
        List<AcademySchedule> academySchedules = academyScheduleRepository.findAll();

        //Then
        assertThat(academyCalendarCreateResults.academyTimeTemplateIds().size()).isEqualTo(academyCalendarCreateParam.lessonScheduleParams().size());
        assertThat(academySchedules.size()).isEqualTo(schedules.size());
        academySchedules.stream()
                .forEach(academySchedule -> {
                    assertThat(schedules).contains(academySchedule.getScheduleDate());
                });
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
                    if(lessonScheduleAccessResult.dayOfWeek() == DayOfWeek.MONDAY) {
                        assertThat(lessonScheduleAccessResult.lessonEndTime()).isEqualTo(savedMondayAcademySchedule.getLessonEndTime());
                        assertThat(lessonScheduleAccessResult.lessonStartTime()).isEqualTo(savedMondayAcademySchedule.getLessonStartTime());
                    }

                    if(lessonScheduleAccessResult.dayOfWeek() == DayOfWeek.FRIDAY) {
                        assertThat(lessonScheduleAccessResult.lessonEndTime()).isEqualTo(savedFridayAcademySchedule.getLessonEndTime());
                        assertThat(lessonScheduleAccessResult.lessonStartTime()).isEqualTo(savedFridayAcademySchedule.getLessonStartTime());
                    }
                });

    }
}
