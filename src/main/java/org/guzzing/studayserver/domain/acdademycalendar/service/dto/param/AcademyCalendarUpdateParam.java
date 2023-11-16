package org.guzzing.studayserver.domain.acdademycalendar.service.dto.param;

import java.time.LocalDate;

/**
 * 앞에서는 String으로 받을 것을 잊지 말자
 */
public record AcademyCalendarUpdateParam (
        LocalDate startDateOfAttendance,
        LocalDate endDateOfAttendance,
        String memo
) {
}
