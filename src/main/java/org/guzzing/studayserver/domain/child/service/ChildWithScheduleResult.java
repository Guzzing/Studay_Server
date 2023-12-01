package org.guzzing.studayserver.domain.child.service;

import java.time.LocalDate;
import java.time.LocalTime;
import org.guzzing.studayserver.domain.child.service.result.ChildrenFindResult.ChildFindResult;

public record ChildWithScheduleResult(
        Long childId,
        String profileImageUrl,
        String nickname,
        String grade,
        LocalDate schedule_date,
        LocalTime lessonStartTime,
        LocalTime lessonEndTime,
        String academyName,
        String lessonSubject
) {

    public static ChildWithScheduleResult of(ChildFindResult childInfo, ChildDateScheduleResult schedule) {
        return new ChildWithScheduleResult(
                childInfo.childId(),
                childInfo.profileImageUrl(),
                childInfo.nickname(),
                childInfo.grade(),
                schedule.schedule_date(),
                schedule.lessonStartTime(),
                schedule.lessonEndTime(),
                schedule.academyName(),
                schedule.lessonSubject()
        );
    }
}
