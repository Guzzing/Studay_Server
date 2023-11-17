package org.guzzing.studayserver.domain.acdademycalendar.service;

import org.guzzing.studayserver.domain.acdademycalendar.model.AcademySchedule;
import org.guzzing.studayserver.domain.acdademycalendar.model.AcademyTimeTemplate;
import org.guzzing.studayserver.domain.acdademycalendar.repository.academyschedule.AcademyScheduleRepository;
import org.guzzing.studayserver.domain.acdademycalendar.repository.academytimetemplate.AcademyTimeTemplateRepository;
import org.guzzing.studayserver.domain.acdademycalendar.repository.dto.AcademyTimeTemplateDateInfo;
import org.guzzing.studayserver.domain.acdademycalendar.service.dto.param.AcademyCalendarCreateParam;
import org.guzzing.studayserver.domain.acdademycalendar.service.dto.param.AcademyCalendarUpdateParam;
import org.guzzing.studayserver.domain.acdademycalendar.service.dto.param.LessonScheduleParam;
import org.guzzing.studayserver.domain.acdademycalendar.service.dto.RepeatPeriod;
import org.guzzing.studayserver.domain.acdademycalendar.service.dto.result.AcademyCalendarCreateResults;
import org.guzzing.studayserver.domain.acdademycalendar.service.dto.result.AcademyCalendarLoadToUpdateResult;
import org.guzzing.studayserver.domain.acdademycalendar.service.dto.result.AcademyCalendarUpdateResults;
import org.guzzing.studayserver.domain.dashboard.DashboardAccessService;
import org.guzzing.studayserver.domain.dashboard.DashboardScheduleAccessResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class AcademyCalendarService {

    private final AcademyScheduleRepository academyScheduleRepository;
    private final AcademyTimeTemplateRepository academyTimeTemplateRepository;
    private final PeriodicStrategy periodicStrategy;
    private final DashboardAccessService dashboardAccessService;

    public AcademyCalendarService(
            AcademyScheduleRepository academyScheduleRepository,
            AcademyTimeTemplateRepository academyTimeTemplateRepository,
            PeriodicStrategy periodicStrategy,
            DashboardAccessService dashboardAccessService) {
        this.academyScheduleRepository = academyScheduleRepository;
        this.academyTimeTemplateRepository = academyTimeTemplateRepository;
        this.periodicStrategy = periodicStrategy;
        this.dashboardAccessService = dashboardAccessService;
    }

    @Transactional
    public AcademyCalendarCreateResults createSchedules(AcademyCalendarCreateParam param) {
        List<AcademyTimeTemplateDateInfo> academyTimeTemplateDateInfos =
                academyTimeTemplateRepository.findAcademyTimeTemplateByDashboardId(param.dashboardId());
        DateTimeOverlapChecker.checkOverlap(
                academyTimeTemplateDateInfos,
                param.startDateOfAttendance(),
                param.startDateOfAttendance()
        );

        List<Long> academyTimeTemplateIds = new ArrayList<>();
        param.lessonScheduleParams().forEach(dashboardSchedule -> {
            AcademyTimeTemplate savedAcademyTimeTemplate = saveAcademyTimeTemplate(param, dashboardSchedule);
            academyTimeTemplateIds.add(savedAcademyTimeTemplate.getId());
            saveAcademySchedules(param, dashboardSchedule, savedAcademyTimeTemplate);
        });

        return AcademyCalendarCreateResults.of(academyTimeTemplateIds);
    }

    private AcademyTimeTemplate saveAcademyTimeTemplate(
            AcademyCalendarCreateParam param,
            LessonScheduleParam dashboardSchedule) {

        AcademyTimeTemplate academyTimeTemplate = AcademyCalendarCreateParam.to(param, dashboardSchedule.dayOfWeek());
        return academyTimeTemplateRepository.save(academyTimeTemplate);
    }

    private void saveAcademySchedules(
            AcademyCalendarCreateParam param,
            LessonScheduleParam dashboardSchedule,
            AcademyTimeTemplate savedAcademyTimeTemplate) {

        List<LocalDate> schedules = periodicStrategy.createSchedules(
                RepeatPeriod.of(
                        param.startDateOfAttendance(),
                        param.endDateOfAttendance(),
                        dashboardSchedule.dayOfWeek(),
                        param.periodicity()
                )
        );

        schedules.stream()
                .map(scheduleDate -> AcademySchedule.of(
                        savedAcademyTimeTemplate,
                        scheduleDate,
                        dashboardSchedule.lessonStartTime(),
                        dashboardSchedule.lessonEndTime()
                ))
                .forEach(academyScheduleRepository::save);
    }

    @Transactional(readOnly = true)
    public AcademyCalendarLoadToUpdateResult loadTimeTemplateToUpdate(Long academyScheduleId) {
        AcademyTimeTemplate academyTimeTemplate = academyScheduleRepository.findDistinctAcademyTimeTemplate(academyScheduleId);
        DashboardScheduleAccessResult dashboardScheduleAccessResult =
                dashboardAccessService.getDashboardSchedule(academyTimeTemplate.getDashboardId());

        return AcademyCalendarLoadToUpdateResult.of(academyTimeTemplate, dashboardScheduleAccessResult);
    }

    @Transactional
    public AcademyCalendarUpdateResults updateTimeTemplate(
            AcademyCalendarUpdateParam academyCalendarUpdateParam
    ) {
        List<AcademyTimeTemplateDateInfo> academyTimeTemplates = academyTimeTemplateRepository.findAcademyTimeTemplateByDashboardId(
                academyCalendarUpdateParam.dashboardId());

        if (academyCalendarUpdateParam.isAllUpdated()) {
            deleteAcademyTimeTemplates(academyTimeTemplates);
            return AcademyCalendarUpdateResults.to(
                    createSchedules(AcademyCalendarCreateParam.from(academyCalendarUpdateParam)));
        }

        deleteAcademySchedulesAfterStartDate(academyTimeTemplates, academyCalendarUpdateParam.startDateOfAttendance());
        changeBeforeTimeTemplateOfEndDate(academyTimeTemplates, academyCalendarUpdateParam.startDateOfAttendance());
        return AcademyCalendarUpdateResults.to(
                createSchedules(AcademyCalendarCreateParam.from(academyCalendarUpdateParam)));
    }

    private void deleteAcademyTimeTemplates(
            List<AcademyTimeTemplateDateInfo> academyTimeTemplates
    ) {
        academyTimeTemplates.forEach(academyTimeTemplate -> {
            academyScheduleRepository.deleteAllByAcademyTimeTemplateId(academyTimeTemplate.getId());
            academyTimeTemplateRepository.deleteById(academyTimeTemplate.getId());
        });
    }

    private void deleteAcademySchedulesAfterStartDate(
            List<AcademyTimeTemplateDateInfo> academyTimeTemplates,
            LocalDate startDateOfAttendance
    ) {
        academyTimeTemplates.forEach(academyTimeTemplate -> {
            academyScheduleRepository.deleteAfterUpdatedStartDate(academyTimeTemplate.getId(), startDateOfAttendance);
        });
    }

    private void changeBeforeTimeTemplateOfEndDate(
            List<AcademyTimeTemplateDateInfo> academyTimeTemplates,
            LocalDate startDateOfAttendance
    ) {
        LocalDate endDateOfAttendance = startDateOfAttendance.minusDays(1);

        academyTimeTemplates.forEach(
                academyTimeTemplateDateInfo -> {
                    AcademyTimeTemplate academyTimeTemplate =
                            academyTimeTemplateRepository.getById(academyTimeTemplateDateInfo.getId());
                    academyTimeTemplate.changeEndDateOfAttendance(endDateOfAttendance);
                }
        );

    }

}