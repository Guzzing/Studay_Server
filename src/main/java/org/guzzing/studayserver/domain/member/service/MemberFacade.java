package org.guzzing.studayserver.domain.member.service;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.guzzing.studayserver.domain.auth.jwt.AuthToken;
import org.guzzing.studayserver.domain.auth.service.AuthService;
import org.guzzing.studayserver.domain.calendar.service.AcademyCalendarService;
import org.guzzing.studayserver.domain.child.service.ChildService;
import org.guzzing.studayserver.domain.child.service.result.ChildrenFindResult.ChildFindResult;
import org.guzzing.studayserver.domain.dashboard.service.DashboardService;
import org.guzzing.studayserver.domain.like.service.LikeService;
import org.guzzing.studayserver.domain.review.service.ReviewService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
public class MemberFacade {

    private final MemberService memberService;
    private final ChildService childService;
    private final AcademyCalendarService calendarService;
    private final DashboardService dashboardService;
    private final LikeService likeService;
    private final ReviewService reviewService;
    private final AuthService authService;

    public MemberFacade(
            final MemberService memberService,
            final ChildService childService,
            final AcademyCalendarService calendarService,
            final DashboardService dashboardService,
            final LikeService likeService,
            final ReviewService reviewService,
            AuthService authService
    ) {
        this.memberService = memberService;
        this.childService = childService;
        this.calendarService = calendarService;
        this.dashboardService = dashboardService;
        this.likeService = likeService;
        this.reviewService = reviewService;
        this.authService = authService;
    }

    @Transactional
    public void removeMember(final long memberId, final AuthToken authToken) {
        List<Long> childIds = childService.findByMemberId(memberId).children()
                .stream()
                .map(ChildFindResult::childId)
                .toList();

        reviewService.removeReview(memberId);
        likeService.removeLike(memberId);
        calendarService.removeCalendar(childIds);
        dashboardService.removeDashboard(childIds);
        childService.removeChild(memberId);
        memberService.remove(memberId);
        authService.secede(authToken);
    }

}
