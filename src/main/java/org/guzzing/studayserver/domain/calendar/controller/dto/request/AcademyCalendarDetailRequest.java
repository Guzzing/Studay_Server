package org.guzzing.studayserver.domain.calendar.controller.dto.request;

import java.time.LocalDate;
import org.guzzing.studayserver.domain.calendar.controller.dto.request.validation.DateStringValidator;
import org.guzzing.studayserver.domain.calendar.facade.dto.AcademyCalendarDetailFacadeParam;

public record AcademyCalendarDetailRequest(
        String requestedDate,
        Long lessonId,
        Long childId,
        Long scheduleId
) {

    public AcademyCalendarDetailRequest {
        DateStringValidator.isValidDate(requestedDate);
        isValidId(lessonId);
        isValidId(childId);
        isValidId(scheduleId);
    }

    public static AcademyCalendarDetailFacadeParam to(
            AcademyCalendarDetailRequest academyCalendarDetailRequest
    ) {
        return new AcademyCalendarDetailFacadeParam(
                LocalDate.parse(academyCalendarDetailRequest.requestedDate()),
                academyCalendarDetailRequest.lessonId(),
                new AcademyCalendarDetailFacadeParam.FacadeChildrenSchedule(
                        academyCalendarDetailRequest.childId(),
                        academyCalendarDetailRequest.scheduleId()
                )
        );
    }

    private static void isValidId(Long lessonId) {
        if (lessonId == null) {
            throw new IllegalArgumentException("id는 null일 수 없습니다.");
        }
        if (lessonId < 0) {
            throw new IllegalArgumentException("id는 음수일 수 없습니다.");
        }
    }

}
