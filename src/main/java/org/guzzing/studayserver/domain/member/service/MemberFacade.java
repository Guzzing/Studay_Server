package org.guzzing.studayserver.domain.member.service;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.guzzing.studayserver.domain.calendar.service.AcademyCalendarService;
import org.guzzing.studayserver.domain.child.service.ChildService;
import org.guzzing.studayserver.domain.child.service.result.ChildrenFindResult.ChildFindResult;
import org.guzzing.studayserver.domain.dashboard.service.DashboardService;
import org.guzzing.studayserver.domain.like.service.LikeCommandService;
import org.guzzing.studayserver.domain.member.model.Member;
import org.guzzing.studayserver.domain.review.service.ReviewFacade;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
public class MemberFacade {

    private final MemberService memberService;
    private final ChildService childService;
    private final AcademyCalendarService calendarService;
    private final DashboardService dashboardService;
    private final LikeCommandService likeCommandService;
    private final ReviewFacade reviewFacade;

    public MemberFacade(
            final MemberService memberService,
            final ChildService childService,
            final AcademyCalendarService calendarService,
            final DashboardService dashboardService,
            final LikeCommandService likeCommandService,
            final ReviewFacade reviewFacade
    ) {
        this.memberService = memberService;
        this.childService = childService;
        this.calendarService = calendarService;
        this.dashboardService = dashboardService;
        this.likeCommandService = likeCommandService;
        this.reviewFacade = reviewFacade;
    }

    @Transactional
    public void removeMember(final long memberId) {
        final Member member = memberService.getMember(memberId);

        final List<Long> childIds = childService.findByMemberId(member.getId())
                .children()
                .stream()
                .map(ChildFindResult::childId)
                .toList();

        reviewFacade.removeReview(member);
        likeCommandService.deleteLikesOfMember(member);
        calendarService.removeCalendar(childIds);
        dashboardService.removeDashboard(childIds);
        childService.removeChild(memberId);
        memberService.remove(memberId);
    }

}
