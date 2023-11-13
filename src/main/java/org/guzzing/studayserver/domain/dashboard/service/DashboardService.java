package org.guzzing.studayserver.domain.dashboard.service;

import java.util.List;
import org.guzzing.studayserver.domain.academy.service.AcademyAccessService;
import org.guzzing.studayserver.domain.child.service.ChildAccessService;
import org.guzzing.studayserver.domain.dashboard.model.Dashboard;
import org.guzzing.studayserver.domain.dashboard.repository.DashboardRepository;
import org.guzzing.studayserver.domain.dashboard.service.converter.DashboardServiceConverter;
import org.guzzing.studayserver.domain.dashboard.service.dto.request.DashboardPostParam;
import org.guzzing.studayserver.domain.dashboard.service.dto.response.DashboardGetResult;
import org.guzzing.studayserver.domain.dashboard.service.dto.response.DashboardGetResults;
import org.guzzing.studayserver.domain.dashboard.service.dto.response.DashboardPostResult;
import org.guzzing.studayserver.domain.dashboard.service.vo.AcademyInfo;
import org.guzzing.studayserver.domain.dashboard.service.vo.ChildInfo;
import org.guzzing.studayserver.domain.dashboard.service.vo.LessonInfo;
import org.guzzing.studayserver.domain.member.service.MemberAccessService;
import org.guzzing.studayserver.global.exception.DashboardException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class DashboardService {

    private final DashboardServiceConverter serviceConverter;
    private final DashboardRepository dashboardRepository;
    private final MemberAccessService memberAccessService;
    private final AcademyAccessService academyAccessService;
    private final ChildAccessService childAccessService;

    public DashboardService(
            final DashboardServiceConverter serviceConverter,
            final DashboardRepository dashboardRepository,
            final MemberAccessService memberAccessService,
            final AcademyAccessService academyAccessService,
            final ChildAccessService childAccessService
    ) {
        this.serviceConverter = serviceConverter;
        this.dashboardRepository = dashboardRepository;
        this.memberAccessService = memberAccessService;
        this.academyAccessService = academyAccessService;
        this.childAccessService = childAccessService;
    }

    @Transactional
    public DashboardPostResult createDashboard(final DashboardPostParam param, final Long memberId) {
        memberAccessService.validateMember(memberId);
        academyAccessService.validateAcademy(param.academyId());
        academyAccessService.validateLesson(param.lessonId());

        final Dashboard dashboard = serviceConverter.to(param);
        final Dashboard savedDashboard = dashboardRepository.save(dashboard);

        return serviceConverter.from(savedDashboard);
    }

    public DashboardGetResult findDashboard(final long dashboardId, final long memberId) {
        memberAccessService.validateMember(memberId);

        final Dashboard dashboard = getDashboard(dashboardId);

        final ChildInfo childInfo = childAccessService.findChildInfo(dashboard.getChildId());
        final AcademyInfo academyInfo = academyAccessService.findAcademyInfo(dashboard.getAcademyId());
        final LessonInfo lessonInfo = academyAccessService.findLessonInfo(dashboard.getLessonId());

        return serviceConverter.from(dashboard, childInfo, academyInfo, lessonInfo);
    }

    public DashboardGetResults findDashboards(final long childId, final boolean activeOnly, final long memberId) {
        memberAccessService.validateMember(memberId);

        final List<Dashboard> dashboards = activeOnly
                ? dashboardRepository.findActiveOnlyByChildId(childId)
                : dashboardRepository.findAllByChildId(childId);

        final List<DashboardGetResult> results = dashboards.stream()
                .map(dashboard -> {
                    final ChildInfo childInfo = childAccessService.findChildInfo(dashboard.getChildId());
                    final AcademyInfo academyInfo = academyAccessService.findAcademyInfo(dashboard.getAcademyId());
                    final LessonInfo lessonInfo = academyAccessService.findLessonInfo(dashboard.getLessonId());

                    return serviceConverter.from(dashboard, childInfo, academyInfo, lessonInfo);
                })
                .toList();

        return serviceConverter.from(results);
    }

    @Transactional
    public void deleteDashboard(final Long dashboardId, final Long memberId) {
        memberAccessService.validateMember(memberId);

        final Dashboard dashboard = getDashboard(dashboardId);

        if (dashboard.isActive()) {
            throw new DashboardException("비활성화된 대시보드만 삭제가 가능합니다.");
        }

        dashboard.delete();
    }

    private Dashboard getDashboard(final long dashboardId) {
        return dashboardRepository.findById(dashboardId)
                .orElseThrow(() -> new DashboardException("존재하지 않는 대시보드 입니다."));
    }

}
