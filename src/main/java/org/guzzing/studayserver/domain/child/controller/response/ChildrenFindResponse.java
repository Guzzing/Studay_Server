package org.guzzing.studayserver.domain.child.controller.response;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.guzzing.studayserver.domain.child.service.ChildWithScheduleResult;

public record ChildrenFindResponse(
        List<ChildFindResponse> children
) {

    public ChildrenFindResponse(List<ChildFindResponse> children) {
        this.children = List.copyOf(children);
    }

    public static ChildrenFindResponse from(List<ChildWithScheduleResult> childWithScheduleResults) {
        List<ChildFindResponse> children = childWithScheduleResults.stream()
                .map(r -> new ChildFindResponse(
                        r.childId(),
                        r.profileImageUrl(),
                        r.nickname(),
                        r.grade(),
                        new ChildScheduleResponse(
                                r.schedule_date(),
                                r.lessonStartTime(),
                                r.lessonEndTime(),
                                r.academyName(),
                                r.lessonSubject()
                        )))
                .toList();

        return new ChildrenFindResponse(children);
    }

    public record ChildFindResponse(
            Long childId,
            String profileImageUrl,
            String nickname,
            String grade,
            ChildScheduleResponse schedule
    ) {

    }

    public record ChildScheduleResponse(
            LocalDate schedule_date,
            LocalTime lessonStartTime,
            LocalTime lessonEndTime,
            String academyName,
            String lessonSubject
    ) {

    }
}
