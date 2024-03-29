package org.guzzing.studayserver.domain.member.service;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.guzzing.studayserver.domain.calendar.service.AcademyCalendarService;
import org.guzzing.studayserver.domain.child.model.Child;
import org.guzzing.studayserver.domain.dashboard.service.DashboardService;
import org.guzzing.studayserver.domain.member.event.WithdrawEvent;
import org.guzzing.studayserver.domain.member.model.Member;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
public class MemberFacade {

    private final MemberService memberService;
    private final AcademyCalendarService calendarService;
    private final DashboardService dashboardService;
    private final ApplicationEventPublisher eventPublisher;

    public MemberFacade(
            final MemberService memberService,
            final AcademyCalendarService calendarService,
            final DashboardService dashboardService,
            final ApplicationEventPublisher eventPublisher
    ) {
        this.memberService = memberService;
        this.calendarService = calendarService;
        this.dashboardService = dashboardService;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    public Long removeMember(final HttpServletRequest request, final Long memberId) {
        try {
            final Member member = memberService.getMember(memberId);
            final List<String> childProfileImageUris = member.getChildren()
                    .stream()
                    .map(Child::getProfileImageURIPath)
                    .toList();
            final List<Long> childIds = member.getChildren()
                    .stream()
                    .map(Child::getId)
                    .toList();

            calendarService.removeCalendar(childIds);
            // todo: 대시보드 엔티티 직접참조 전환하면 여기 수정할 것
            dashboardService.removeDashboard(childIds);
            memberService.remove(memberId);

            eventPublisher.publishEvent(new WithdrawEvent(request, childProfileImageUris));

            return memberId;
        } catch (Exception e) {
            log.info("회원 탈퇴 중 에러가 발생했습니다. memberId: {}", memberId, e);
            throw e;
        }
    }

}
