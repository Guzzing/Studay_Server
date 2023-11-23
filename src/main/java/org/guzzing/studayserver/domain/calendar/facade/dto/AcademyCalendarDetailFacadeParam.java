package org.guzzing.studayserver.domain.calendar.facade.dto;

import org.guzzing.studayserver.domain.calendar.service.dto.param.AcademyCalendarDetailParam;

import java.time.LocalDate;
import java.util.List;

public record AcademyCalendarDetailFacadeParam(
        LocalDate requestedDate,
        Long lessonId,
        List<FacadeChildrenSchedule> childrenInfos
) {

    public static AcademyCalendarDetailParam to(AcademyCalendarDetailFacadeParam param) {
        return new AcademyCalendarDetailParam(
                param.childrenInfos()
                        .stream()
                        .map(childrenInfo -> FacadeChildrenSchedule.to(childrenInfo))
                        .toList()
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
        return childrenInfos.stream()
                .map(childrenSchedules -> childrenSchedules.childId)
                .toList();
    }

}
