package org.guzzing.studayserver.domain.calendar.controller.dto.request;

import org.guzzing.studayserver.domain.calendar.controller.dto.request.validation.DateStringValidator;
import org.guzzing.studayserver.domain.calendar.facade.dto.AcademyCalendarDetailFacadeParam;

import java.time.LocalDate;
import java.util.List;

public record AcademyCalendarDetailRequest(
        String requestedDate,
        Long lessonId,
        List<ChildrenScheduleDetailRequest> childrenInfos
) {

    public AcademyCalendarDetailRequest {
        DateStringValidator.isValidDate(requestedDate);
        isValidLessonId(lessonId);
        isValidChildrenInfos(childrenInfos);
    }

    public static AcademyCalendarDetailFacadeParam to(
            AcademyCalendarDetailRequest academyCalendarDetailRequest
    ) {
        return new AcademyCalendarDetailFacadeParam(
                LocalDate.parse(academyCalendarDetailRequest.requestedDate()),
                academyCalendarDetailRequest.lessonId(),
                academyCalendarDetailRequest.childrenInfos.stream()
                        .map(childrenScheduleDetailRequest
                                -> ChildrenScheduleDetailRequest.to(childrenScheduleDetailRequest))
                        .toList()
        );
    }

    public record ChildrenScheduleDetailRequest(
            Long childId,
            Long scheduleId
    ) {
        public static AcademyCalendarDetailFacadeParam.FacadeChildrenSchedule to(
                ChildrenScheduleDetailRequest childrenScheduleDetailRequest
        ) {
            return new AcademyCalendarDetailFacadeParam.FacadeChildrenSchedule(
                    childrenScheduleDetailRequest.childId(),
                    childrenScheduleDetailRequest.scheduleId()
            );
        }
    }

    private static void isValidLessonId(Long lessonId) {
        if (lessonId == null) {
            throw new IllegalArgumentException("id는 null일 수 없습니다.");
        }
        if (lessonId < 0) {
            throw new IllegalArgumentException("id는 음수일 수 없습니다.");
        }
    }

    private static void isValidChildrenInfos(List<ChildrenScheduleDetailRequest> childrenInfos) {
        if (childrenInfos == null || childrenInfos.isEmpty()) {
            throw new IllegalArgumentException("아이에 대한 정보는 빈 값일 수 없습니다.");
        }
    }

}
