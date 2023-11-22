package org.guzzing.studayserver.domain.dashboard.facade;

import java.time.LocalDate;
import java.util.List;
import org.guzzing.studayserver.domain.academy.service.AcademyAccessService;
import org.guzzing.studayserver.domain.calendar.service.AcademyCalendarAccessService;
import org.guzzing.studayserver.domain.calendar.service.dto.param.AcademyCalendarDeleteByDashboardParam;
import org.guzzing.studayserver.domain.child.service.ChildAccessService;
import org.guzzing.studayserver.domain.dashboard.facade.converter.DashboardFacadeConverter;
import org.guzzing.studayserver.domain.dashboard.facade.dto.DashboardGetResult;
import org.guzzing.studayserver.domain.dashboard.facade.dto.DashboardGetResults;
import org.guzzing.studayserver.domain.dashboard.facade.dto.DashboardPatchResult;
import org.guzzing.studayserver.domain.dashboard.facade.dto.DashboardPostResult;
import org.guzzing.studayserver.domain.dashboard.facade.dto.DashboardPutResult;
import org.guzzing.studayserver.domain.dashboard.facade.vo.AcademyInfo;
import org.guzzing.studayserver.domain.dashboard.facade.vo.ChildInfo;
import org.guzzing.studayserver.domain.dashboard.facade.vo.LessonInfo;
import org.guzzing.studayserver.domain.dashboard.service.DashboardService;
import org.guzzing.studayserver.domain.dashboard.service.dto.request.DashboardPostParam;
import org.guzzing.studayserver.domain.dashboard.service.dto.request.DashboardPutParam;
import org.guzzing.studayserver.domain.dashboard.service.dto.response.DashboardResult;
import org.guzzing.studayserver.domain.member.service.MemberAccessService;
import org.springframework.stereotype.Component;

@Component
public class DashboardFacade {

    private final DashboardFacadeConverter facadeConverter;
    private final DashboardService dashboardService;
    private final AcademyCalendarAccessService academyCalendarAccessService;
    private final MemberAccessService memberAccessService;
    private final AcademyAccessService academyAccessService;
    private final ChildAccessService childAccessService;

    public DashboardFacade(
            final DashboardFacadeConverter facadeConverter,
            final DashboardService dashboardService,
            final AcademyCalendarAccessService academyCalendarAccessService,
            final MemberAccessService memberAccessService,
            final AcademyAccessService academyAccessService,
            final ChildAccessService childAccessService
    ) {
        this.facadeConverter = facadeConverter;
        this.dashboardService = dashboardService;
        this.academyCalendarAccessService = academyCalendarAccessService;
        this.memberAccessService = memberAccessService;
        this.academyAccessService = academyAccessService;
        this.childAccessService = childAccessService;
    }

    public DashboardPostResult createDashboard(final DashboardPostParam param, final Long memberId) {
        memberAccessService.validateMember(memberId);
        academyAccessService.validateAcademy(param.academyId());
        academyAccessService.validateLesson(param.academyId(), param.lessonId());

        final DashboardResult result = dashboardService.saveDashboard(param);

        return facadeConverter.postFromResult(result);
    }

    public void removeDashboard(final Long dashboardId, final Long memberId) {
        memberAccessService.validateMember(memberId);

        dashboardService.deleteDashboard(dashboardId);

        final AcademyCalendarDeleteByDashboardParam deleteParam = new AcademyCalendarDeleteByDashboardParam(
                dashboardId, LocalDate.now());
        academyCalendarAccessService.deleteSchedule(deleteParam);
    }

    public DashboardPutResult modifyDashboard(final DashboardPutParam param, final Long memberId) {
        memberAccessService.validateMember(memberId);

        final DashboardResult result = dashboardService.editDashboard(param);

        return facadeConverter.putFromResult(result);
    }

    public DashboardPatchResult revertDashboardActiveness(final long dashboardId, final long memberId) {
        memberAccessService.validateMember(memberId);

        final DashboardResult result = dashboardService.toggleDashboardActiveness(dashboardId);

        return facadeConverter.patchFromResult(result);
    }

    public DashboardGetResult getDashboard(final long dashboardId, final Long memberId) {
        memberAccessService.validateMember(memberId);

        final DashboardResult result = dashboardService.findDashboard(dashboardId);

        return getDashboardInfo(result);
    }

    public DashboardGetResults getDashboards(final long childId, final boolean activeOnly, final long memberId) {
        memberAccessService.validateMember(memberId);
        memberAccessService.validateChild(memberId, childId);

        final List<DashboardResult> dashboards = dashboardService.findDashboards(childId, activeOnly);

        final List<DashboardGetResult> results = dashboards.stream()
                .map(this::getDashboardInfo)
                .toList();

        return facadeConverter.getFromResults(results);
    }

    private DashboardGetResult getDashboardInfo(DashboardResult result) {
        final ChildInfo childInfo = childAccessService.findChildInfo(result.childId());
        final AcademyInfo academyInfo = academyAccessService.findAcademyInfo(result.academyId());
        final LessonInfo lessonInfo = academyAccessService.findLessonInfo(result.lessonId());

        return facadeConverter.getFromResult(result, childInfo, academyInfo, lessonInfo);
    }

}
