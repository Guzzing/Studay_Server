package org.guzzing.studayserver.domain.calendar.service;

import java.time.LocalDate;
import java.util.List;

import org.guzzing.studayserver.domain.calendar.model.AcademySchedule;
import org.guzzing.studayserver.domain.calendar.model.AcademyTimeTemplate;
import org.guzzing.studayserver.domain.calendar.repository.academyschedule.AcademyScheduleRepository;
import org.guzzing.studayserver.domain.calendar.repository.academytimetemplate.AcademyTimeTemplateRepository;
import org.guzzing.studayserver.domain.calendar.repository.dto.AcademyCalenderDetailInfo;
import org.guzzing.studayserver.domain.calendar.repository.dto.AcademyTimeTemplateDateInfo;
import org.guzzing.studayserver.domain.calendar.service.dto.GeneratedLessonSchedule;
import org.guzzing.studayserver.domain.calendar.service.dto.RepeatPeriod;
import org.guzzing.studayserver.domain.calendar.service.dto.param.AcademyCalendarCreateParam;
import org.guzzing.studayserver.domain.calendar.service.dto.param.AcademyCalendarDeleteByDashboardParam;
import org.guzzing.studayserver.domain.calendar.service.dto.param.AcademyCalendarDeleteParam;
import org.guzzing.studayserver.domain.calendar.service.dto.param.AcademyCalendarDetailParam;
import org.guzzing.studayserver.domain.calendar.service.dto.param.AcademyCalendarUpdateParam;
import org.guzzing.studayserver.domain.calendar.service.dto.result.AcademyCalendarCreateResults;
import org.guzzing.studayserver.domain.calendar.service.dto.result.AcademyCalendarDetailResult;
import org.guzzing.studayserver.domain.calendar.service.dto.result.AcademyCalendarLoadToUpdateResult;
import org.guzzing.studayserver.domain.calendar.service.dto.result.AcademyCalendarUpdateResults;
import org.guzzing.studayserver.domain.dashboard.service.access.DashboardAccessService;
import org.guzzing.studayserver.domain.dashboard.service.access.dto.DashboardScheduleAccessResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        List<AcademySchedule> existedDateOverlappingSchedules =
                academyScheduleRepository.findByDate(
                        param.startDateOfAttendance(),
                        param.endDateOfAttendance(),
                        param.childId());
        List<GeneratedLessonSchedule> generatedSchedules = generateAcademySchedules(param);
        DateTimeOverlapChecker.checkOverlap(existedDateOverlappingSchedules, generatedSchedules);

        List<AcademyTimeTemplate> academyTimeTemplates = saveAcademyTimeTemplate(param);
        academyTimeTemplates
                .forEach(academyTimeTemplate -> saveAcademySchedule(generatedSchedules, academyTimeTemplate));

        return AcademyCalendarCreateResults.of(academyTimeTemplates);
    }


    private boolean isSameTimeTemplate(
            GeneratedLessonSchedule generatedLessonSchedule,
            AcademyTimeTemplate academyTimeTemplate) {
        return generatedLessonSchedule.dayOfWeek().equals(academyTimeTemplate.getDayOfWeek());
    }

    private void saveAcademySchedule(
            List<GeneratedLessonSchedule> generatedLessonSchedules,
            AcademyTimeTemplate savedAcademyTimeTemplate
    ) {
        generatedLessonSchedules
                .stream()
                .filter(generatedLessonSchedule -> isSameTimeTemplate(generatedLessonSchedule, savedAcademyTimeTemplate))
                .forEach(generatedLessonSchedule -> academyScheduleRepository.save(
                        AcademySchedule.of(
                                savedAcademyTimeTemplate,
                                generatedLessonSchedule.scheduleDate(),
                                generatedLessonSchedule.lessonStartTime(),
                                generatedLessonSchedule.lessonEndTime())
                ));
    }

    private List<AcademyTimeTemplate> saveAcademyTimeTemplate(
            AcademyCalendarCreateParam param) {
        return param.lessonScheduleParams()
                .stream()
                .map(
                        lessonScheduleParam ->
                                academyTimeTemplateRepository.save(AcademyCalendarCreateParam.to(param, lessonScheduleParam.dayOfWeek())))
                .toList();
    }

    private List<GeneratedLessonSchedule> generateAcademySchedules(
            AcademyCalendarCreateParam param) {
        return param.lessonScheduleParams()
                .stream()
                .flatMap(lessonScheduleParam ->
                        periodicStrategy.createSchedules(
                                        RepeatPeriod.of(
                                                param.startDateOfAttendance(),
                                                param.endDateOfAttendance(),
                                                lessonScheduleParam.dayOfWeek(),
                                                param.periodicity()
                                        ))
                                .stream()
                                .map(
                                        scheduleDate ->
                                                GeneratedLessonSchedule.of(
                                                        scheduleDate,
                                                        lessonScheduleParam.lessonStartTime(),
                                                        lessonScheduleParam.lessonEndTime(),
                                                        lessonScheduleParam.dayOfWeek()
                                                )
                                )
                )
                .toList();
    }

    @Transactional(readOnly = true)
    public AcademyCalendarLoadToUpdateResult loadTimeTemplateToUpdate(Long academyScheduleId) {
        AcademyTimeTemplate academyTimeTemplate = academyScheduleRepository.findDistinctAcademyTimeTemplate(
                academyScheduleId);
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

    @Transactional
    public void deleteSchedule(AcademyCalendarDeleteParam academyCalendarDeleteParam) {
        if (!academyCalendarDeleteParam.isAllDeleted()) {
            academyScheduleRepository.deleteAcademyScheduleById(academyCalendarDeleteParam.academyScheduleId());
            return;
        }

        Long dashboardId = academyScheduleRepository.findDashboardIdByAcademyScheduleId(
                academyCalendarDeleteParam.academyScheduleId());
        List<AcademyTimeTemplateDateInfo> academyTimeTemplates =
                academyTimeTemplateRepository.findAcademyTimeTemplateByDashboardId(dashboardId);
        LocalDate requestedDate
                = academyScheduleRepository.findScheduleDate(academyCalendarDeleteParam.academyScheduleId());

        deleteAcademySchedulesAfterStartDate(academyTimeTemplates, requestedDate);
        changeBeforeTimeTemplateOfEndDate(academyTimeTemplates, requestedDate);
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

    @Transactional
    public void deleteSchedulesByDashboard(AcademyCalendarDeleteByDashboardParam param) {
        List<AcademyTimeTemplateDateInfo> academyTimeTemplates
                = academyTimeTemplateRepository.findAcademyTimeTemplateByDashboardId(param.dashboardId());

        deleteAcademySchedulesAfterStartDate(academyTimeTemplates, param.requestedDate());
        changeBeforeTimeTemplateOfEndDate(academyTimeTemplates, param.requestedDate());
    }

    @Transactional
    public void removeCalendar(final List<Long> childIds) {
        academyTimeTemplateRepository.findByChildIdIn(childIds)
                .forEach(academyScheduleRepository::deleteAllByAcademyTimeTemplateId);
        academyTimeTemplateRepository.deleteAllByChildIds(childIds);
    }

    @Transactional(readOnly = true)
    public AcademyCalendarDetailResult detailSchedules(AcademyCalendarDetailParam param) {
        AcademyCalenderDetailInfo academyCalenderDetailInfo
                = academyScheduleRepository.findTimeTemplateByChildIdAndScheduleId(param.scheduleId(), param.childId());

        LocalDate requestedDate = academyScheduleRepository.findScheduleDate(param.scheduleId());
        return AcademyCalendarDetailResult.from(academyCalenderDetailInfo, requestedDate);
    }

}
