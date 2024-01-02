package org.guzzing.studayserver.domain.member.service;

import java.util.List;
import org.guzzing.studayserver.domain.calendar.service.AcademyCalendarService;
import org.guzzing.studayserver.domain.child.service.ChildService;
import org.guzzing.studayserver.domain.child.service.result.ChildrenFindResult.ChildFindResult;
import org.guzzing.studayserver.domain.dashboard.service.DashboardService;
import org.guzzing.studayserver.domain.member.model.Member;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class MemberFacade {

    private final MemberService memberService;
    private final ChildService childService;
    private final AcademyCalendarService calendarService;
    private final DashboardService dashboardService;

    public MemberFacade(
            final MemberService memberService,
            final ChildService childService,
            final AcademyCalendarService calendarService,
            final DashboardService dashboardService
    ) {
        this.memberService = memberService;
        this.childService = childService;
        this.calendarService = calendarService;
        this.dashboardService = dashboardService;
    }

    @Transactional
    public void removeMember(final Long memberId) {
        final Member member = memberService.getMember(memberId);

        final List<Long> childIds = childService.findByMemberId(member.getId())
                .children()
                .stream()
                .map(ChildFindResult::childId)
                .toList();

        calendarService.removeCalendar(childIds);
        // todo: 대시보드 엔티티 직접참조 전환하면 여기 수정할 것
        dashboardService.removeDashboard(childIds);
        memberService.remove(memberId);
    }

}
