package org.guzzing.studayserver.domain.calendar.controller.dto.request;

import org.guzzing.studayserver.domain.calendar.facade.dto.AcademyCalendarDetailFacadeParam;

public record AcademyCalendarDetailRequest(
        Long lessonId,
        Long childId
) {
    public AcademyCalendarDetailRequest {
        isValidId(lessonId);
        isValidId(childId);
    }

    public static AcademyCalendarDetailFacadeParam to(
            Long scheduleId,
            AcademyCalendarDetailRequest academyCalendarDetailRequest
    ) {
        return new AcademyCalendarDetailFacadeParam(
                academyCalendarDetailRequest.lessonId(),
                academyCalendarDetailRequest.childId(),
                scheduleId
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
