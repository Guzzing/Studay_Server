package org.guzzing.studayserver.domain.calendar.facade;

import org.guzzing.studayserver.domain.calendar.facade.dto.AcademyScheduleLoadToUpdateFacadeResult;
import org.guzzing.studayserver.domain.calendar.model.AcademySchedule;
import org.guzzing.studayserver.domain.calendar.model.AcademyTimeTemplate;
import org.guzzing.studayserver.domain.calendar.repository.academyschedule.AcademyScheduleRepository;
import org.guzzing.studayserver.domain.calendar.repository.academytimetemplate.AcademyTimeTemplateRepository;
import org.guzzing.studayserver.domain.calendar.service.AcademyCalendarService;
import org.guzzing.studayserver.domain.calendar.service.dto.param.AcademyCalendarCreateParam;
import org.guzzing.studayserver.domain.calendar.service.dto.result.AcademyCalendarCreateResults;
import org.guzzing.studayserver.domain.dashboard.fixture.DashboardFixture;
import org.guzzing.studayserver.domain.dashboard.model.Dashboard;
import org.guzzing.studayserver.domain.dashboard.repository.DashboardRepository;
import org.guzzing.studayserver.testutil.fixture.academycalender.AcademyCalenderFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

@Transactional
@SpringBootTest(webEnvironment = NONE)
public class AcademyCalendarFacadeTest {
    @Autowired
    private DashboardRepository dashboardRepository;

    @Autowired
    private DashboardFixture dashboardFixture;

    @Autowired
    private AcademyTimeTemplateRepository academyTimeTemplateRepository;

    @Autowired
    private AcademyScheduleRepository academyScheduleRepository;

    @Autowired
    private AcademyCalendarFacade academyCalendarFacade;

    @Autowired
    private AcademyCalendarService academyCalendarService;

    @Test
    @DisplayName("스케줄 업데이트 전 기존 글을 불러올 때 해당 스케줄에 대한 정보와 대시보드에 대한 정보가 올바르게 반환되는지 확인한다.")
    void loadTimeTemplateToUpdate() {
        //Given
        Dashboard savedDashboard = dashboardRepository.save(dashboardFixture.makeEntity());

        AcademyCalendarCreateParam academyCalendarCreateParam =
                AcademyCalenderFixture.firstChildAcademyCalenderCreateParam();
        AcademyCalendarCreateResults createdTimeTemplates = academyCalendarService.createSchedules(academyCalendarCreateParam);

        Long timeTemplateId = createdTimeTemplates.academyTimeTemplateIds().get(0);
        AcademyTimeTemplate academyTimeTemplate = academyTimeTemplateRepository.getById(timeTemplateId);
        List<AcademySchedule> academySchedules = academyScheduleRepository.findByAcademyTimeTemplateId(timeTemplateId);
        AcademySchedule academySchedule = academySchedules.get(0);
        Long scheduleId =  academySchedule.getId();

        //When
        AcademyScheduleLoadToUpdateFacadeResult academyScheduleLoadToUpdateFacadeResult
                = academyCalendarFacade.loadTimeTemplateToUpdate(scheduleId);

        //Then
        assertAll("AcademySchedule Load To Update",
                () -> assertThat(academyScheduleLoadToUpdateFacadeResult.academyId()).isEqualTo(savedDashboard.getAcademyId()),
                () -> assertThat(academyScheduleLoadToUpdateFacadeResult.childId()).isEqualTo(savedDashboard.getChildId()),
                () -> assertThat(academyScheduleLoadToUpdateFacadeResult.dashboardId()).isEqualTo(savedDashboard.getId()),
                () -> assertThat(academyScheduleLoadToUpdateFacadeResult.lessonId()).isEqualTo(savedDashboard.getLessonId()),
                () -> assertThat(academyScheduleLoadToUpdateFacadeResult.endDateOfAttendance()).isEqualTo(academyTimeTemplate.getEndDateOfAttendance()),
                () -> assertThat(academyScheduleLoadToUpdateFacadeResult.startDateOfAttendance()).isEqualTo(academyTimeTemplate.getStartDateOfAttendance()),
                () -> assertThat(academyScheduleLoadToUpdateFacadeResult.memo()).isEqualTo(academyTimeTemplate.getMemo())
        );
    }


}
