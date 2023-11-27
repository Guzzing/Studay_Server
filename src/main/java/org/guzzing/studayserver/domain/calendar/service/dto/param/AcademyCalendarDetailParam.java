package org.guzzing.studayserver.domain.calendar.service.dto.param;

import java.util.List;

public record AcademyCalendarDetailParam(
        List<ChildrenSchedule> childrenInfos
) {

    public record ChildrenSchedule(
            Long childId,
            Long scheduleId
    ) {

    }

}
