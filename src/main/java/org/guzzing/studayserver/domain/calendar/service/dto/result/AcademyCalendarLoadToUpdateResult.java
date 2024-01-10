package org.guzzing.studayserver.domain.calendar.service.dto.result;

import java.time.LocalDate;
import org.guzzing.studayserver.domain.calendar.model.AcademyTimeTemplate;

public record AcademyCalendarLoadToUpdateResult(
        Long dashboardId,
        LocalDate startDateOfAttendance,
        LocalDate endDateOfAttendance,
        boolean isAlarmed,
        String memo
) {

    public static AcademyCalendarLoadToUpdateResult of(
            AcademyTimeTemplate academyTimeTemplate
    ) {
        return new AcademyCalendarLoadToUpdateResult(
                academyTimeTemplate.getDashboardId(),
                academyTimeTemplate.getStartDateOfAttendance(),
                academyTimeTemplate.getEndDateOfAttendance(),
                academyTimeTemplate.isAlarmed(),
                academyTimeTemplate.getMemo()
        );
    }

}
