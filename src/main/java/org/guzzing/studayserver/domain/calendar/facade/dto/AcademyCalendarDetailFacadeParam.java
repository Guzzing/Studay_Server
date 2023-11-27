package org.guzzing.studayserver.domain.calendar.facade.dto;

import java.time.LocalDate;
import java.util.List;
import org.guzzing.studayserver.domain.calendar.service.dto.param.AcademyCalendarDetailParam;

public record AcademyCalendarDetailFacadeParam(
        LocalDate requestedDate,
        Long lessonId,
        FacadeChildrenSchedule childrenInfos
) {

    public static AcademyCalendarDetailParam to(AcademyCalendarDetailFacadeParam param) {
        return new AcademyCalendarDetailParam(
                List.of(new AcademyCalendarDetailParam.ChildrenSchedule(param.childrenInfos.childId
                        , param.childrenInfos.scheduleId))
        );
    }

    public record FacadeChildrenSchedule(
            Long childId,
            Long scheduleId
    ) {

        public static AcademyCalendarDetailParam.ChildrenSchedule to(
                AcademyCalendarDetailFacadeParam.FacadeChildrenSchedule facadeChildrenSchedule
        ) {
            return new AcademyCalendarDetailParam.ChildrenSchedule(
                    facadeChildrenSchedule.childId(),
                    facadeChildrenSchedule.scheduleId()
            );
        }
    }

    public List<Long> getChildrenIds() {
        return List.of(childrenInfos.childId);
    }

}
