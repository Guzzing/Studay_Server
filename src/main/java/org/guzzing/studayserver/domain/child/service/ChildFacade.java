package org.guzzing.studayserver.domain.child.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;
import org.guzzing.studayserver.domain.child.service.result.ChildrenFindResult;
import org.guzzing.studayserver.domain.child.service.result.ChildrenFindResult.ChildFindResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class ChildFacade {

    private final ChildService childService;
    private final ChildScheduleQuery childScheduleQuery;

    public ChildFacade(ChildService childService, ChildScheduleQuery childScheduleQuery) {
        this.childService = childService;
        this.childScheduleQuery = childScheduleQuery;
    }

    public List<ChildWithScheduleResult> findChildrenByMemberIdAndDateTime(Long memberId, LocalDateTime dateTime) {
        ChildrenFindResult childrenFindResult = childService.findByMemberId(memberId);

        List<ChildDateScheduleResult> childDateScheduleResults =
                childScheduleQuery.findScheduleByMemberIdAndDate(memberId, dateTime.toLocalDate(),
                        dateTime.toLocalTime());

        return childrenFindResult.children().stream()
                .flatMap(childInfo -> matchChildWithSchedule(childInfo, childDateScheduleResults, dateTime))
                .toList();
    }

    private Stream<ChildWithScheduleResult> matchChildWithSchedule(
            ChildFindResult childInfo, List<ChildDateScheduleResult> schedules, LocalDateTime dateTime) {
        return schedules.stream()
                .filter(schedule -> childInfo.childId().equals(schedule.childId()))
                .findFirst()
                .map(Stream::of)
                .orElseGet(() -> Stream.of(createEmptyChildDateScheduleResult(childInfo, dateTime)))
                .map(schedule -> ChildWithScheduleResult.of(childInfo, schedule));
    }

    private ChildDateScheduleResult createEmptyChildDateScheduleResult(ChildFindResult childInfo,
            LocalDateTime dateTime) {
        return new ChildDateScheduleResult(
                childInfo.childId(),
                dateTime.toLocalDate(),
                dateTime.toLocalTime(),
                dateTime.toLocalTime(),
                "수행 중인 학원이 없습니다.",
                "수행 중인 수업이 없습니다."
        );
    }
}
