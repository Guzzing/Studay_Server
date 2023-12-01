package org.guzzing.studayserver.domain.child.service;

import java.time.LocalDateTime;
import java.util.List;
import org.guzzing.studayserver.domain.child.service.result.ChildrenFindResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class ChildFacade {

    private final ChildService childService;
    private final ChildScheduleReadService childScheduleReadService;

    public ChildFacade(ChildService childService, ChildScheduleReadService childScheduleReadService) {
        this.childService = childService;
        this.childScheduleReadService = childScheduleReadService;
    }

    public List<ChildWithScheduleResult> findChildrenByMemberIdAndDateTime(Long memberId, LocalDateTime dateTime) {
        ChildrenFindResult childrenFindResult = childService.findByMemberId(memberId);

        List<ChildDateScheduleResult> childDateScheduleResults = childScheduleReadService.findScheduleByMemberIdAndDate(
                memberId, dateTime.toLocalDate());

        List<ChildWithScheduleResult> childWithScheduleResults = childrenFindResult.children().stream()
                .flatMap(childInfo ->
                        childDateScheduleResults.stream()
                                .filter(schedule -> childInfo.childId().equals(schedule.childId()))
                                .map(schedule -> ChildWithScheduleResult.of(childInfo, schedule))
                ).toList();

        return childWithScheduleResults.stream()
                .filter(r -> !r.lessonStartTime().isAfter(dateTime.toLocalTime()))
                .filter(r -> !r.lessonEndTime().isBefore(dateTime.toLocalTime()))
                .toList();
    }
}
