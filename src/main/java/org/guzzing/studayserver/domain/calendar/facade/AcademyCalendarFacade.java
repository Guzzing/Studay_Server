package org.guzzing.studayserver.domain.calendar.facade;

import org.guzzing.studayserver.domain.academy.service.AcademyAccessService;
import org.guzzing.studayserver.domain.academy.service.dto.result.AcademyAndLessonDetailResult;
import org.guzzing.studayserver.domain.calendar.facade.dto.AcademyCalendarDetailFacadeParam;
import org.guzzing.studayserver.domain.calendar.facade.dto.AcademyCalendarDetailFacadeResult;
import org.guzzing.studayserver.domain.calendar.facade.dto.AcademyScheduleLoadToUpdateFacadeResult;
import org.guzzing.studayserver.domain.calendar.service.AcademyCalendarService;
import org.guzzing.studayserver.domain.calendar.service.dto.result.AcademyCalendarDetailResult;
import org.guzzing.studayserver.domain.calendar.service.dto.result.AcademyCalendarLoadToUpdateResult;
import org.guzzing.studayserver.domain.child.service.ChildAccessService;
import org.guzzing.studayserver.domain.child.service.result.AcademyCalendarDetailChildInfo;
import org.guzzing.studayserver.domain.dashboard.service.DashboardService;
import org.guzzing.studayserver.domain.dashboard.service.dto.response.DashboardScheduleAccessResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AcademyCalendarFacade {

    private final AcademyAccessService academyService;
    private final ChildAccessService childService;
    private final AcademyCalendarService academyCalendarService;
    private final DashboardService dashboardService;

    public AcademyCalendarFacade(AcademyAccessService academyService, ChildAccessService childService,
                                 AcademyCalendarService academyCalendarService, DashboardService dashboardService) {
        this.academyService = academyService;
        this.childService = childService;
        this.academyCalendarService = academyCalendarService;
        this.dashboardService = dashboardService;
    }

    @Transactional(readOnly = true)
    public AcademyCalendarDetailFacadeResult getCalendarDetailInfo(AcademyCalendarDetailFacadeParam param) {
        AcademyAndLessonDetailResult academyAndLessonDetailResult = academyService.getAcademyAndLessonDetail(
                param.lessonId());
        AcademyCalendarDetailChildInfo childImage = childService.getChildImages(param.childId());

        AcademyCalendarDetailResult academyCalendarDetailResult = academyCalendarService.detailSchedules(
                AcademyCalendarDetailFacadeParam.to(param));

        return AcademyCalendarDetailFacadeResult.from(
                academyAndLessonDetailResult,
                childImage,
                academyCalendarDetailResult,
                academyCalendarDetailResult.requestedDate()
        );

    }

    @Transactional(readOnly = true)
    public AcademyScheduleLoadToUpdateFacadeResult loadTimeTemplateToUpdate(Long academyScheduleId) {
        AcademyCalendarLoadToUpdateResult academyCalendarLoadToUpdateResult = academyCalendarService.loadTimeTemplateToUpdate(academyScheduleId);
        DashboardScheduleAccessResult dashboardScheduleAccessResult = dashboardService.getDashboardSchedule(academyCalendarLoadToUpdateResult.dashboardId());

        return AcademyScheduleLoadToUpdateFacadeResult.from(
                academyCalendarLoadToUpdateResult,
                dashboardScheduleAccessResult);
    }

}
