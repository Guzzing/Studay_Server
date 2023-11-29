package org.guzzing.studayserver.domain.calendar.facade;

import org.guzzing.studayserver.domain.academy.service.AcademyAccessService;
import org.guzzing.studayserver.domain.academy.service.dto.result.AcademyAndLessonDetailResult;
import org.guzzing.studayserver.domain.calendar.facade.dto.AcademyCalendarDetailFacadeParam;
import org.guzzing.studayserver.domain.calendar.facade.dto.AcademyCalendarDetailFacadeResult;
import org.guzzing.studayserver.domain.calendar.service.AcademyCalendarService;
import org.guzzing.studayserver.domain.calendar.service.dto.result.AcademyCalendarDetailResult;
import org.guzzing.studayserver.domain.child.service.ChildAccessService;
import org.guzzing.studayserver.domain.child.service.result.AcademyCalendarDetailChildInfo;
import org.springframework.stereotype.Service;

@Service
public class AcademyCalendarFacade {

    private final AcademyAccessService academyService;
    private final ChildAccessService childService;
    private final AcademyCalendarService academyCalendarService;

    public AcademyCalendarFacade(AcademyAccessService academyService, ChildAccessService childService,
                                 AcademyCalendarService academyCalendarService) {
        this.academyService = academyService;
        this.childService = childService;
        this.academyCalendarService = academyCalendarService;
    }

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

}
