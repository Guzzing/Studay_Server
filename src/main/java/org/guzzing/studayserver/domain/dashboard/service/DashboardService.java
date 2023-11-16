package org.guzzing.studayserver.domain.dashboard.service;

import java.util.List;
import org.guzzing.studayserver.domain.academy.service.AcademyAccessService;
import org.guzzing.studayserver.domain.child.service.ChildAccessService;
import org.guzzing.studayserver.domain.dashboard.model.Dashboard;
import org.guzzing.studayserver.domain.dashboard.model.vo.FeeInfo;
import org.guzzing.studayserver.domain.dashboard.model.vo.SimpleMemoType;
import org.guzzing.studayserver.domain.dashboard.repository.DashboardRepository;
import org.guzzing.studayserver.domain.dashboard.service.converter.DashboardServiceConverter;
import org.guzzing.studayserver.domain.dashboard.service.dto.request.DashboardPostParam;
import org.guzzing.studayserver.domain.dashboard.service.dto.request.DashboardPutParam;
import org.guzzing.studayserver.domain.dashboard.service.dto.response.DashboardGetResult;
import org.guzzing.studayserver.domain.dashboard.service.dto.response.DashboardGetResults;
import org.guzzing.studayserver.domain.dashboard.service.dto.response.DashboardPatchResult;
import org.guzzing.studayserver.domain.dashboard.service.dto.response.DashboardPostResult;
import org.guzzing.studayserver.domain.dashboard.service.dto.response.DashboardPutResult;
import org.guzzing.studayserver.domain.dashboard.service.vo.AcademyInfo;
import org.guzzing.studayserver.domain.dashboard.service.vo.ChildInfo;
import org.guzzing.studayserver.domain.dashboard.service.vo.LessonInfo;
import org.guzzing.studayserver.domain.member.service.MemberAccessService;
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

        return serviceConverter.postResultFrom(savedDashboard);
    }

    @Transactional
    public DashboardPutResult editDashboard(final DashboardPutParam param, final Long memberId) {
        memberAccessService.validateMember(memberId);

        final FeeInfo feeInfo = serviceConverter.convertToFeeInfo(param.paymentInfo());
        final List<SimpleMemoType> simpleMemoTypes = serviceConverter.convertToSelectedSimpleMemoTypes(
                param.simpleMemoTypeMap());

        final Dashboard dashboard = dashboardRepository.findDashboardById(param.dashboardId())
                .updateFeeInfo(feeInfo)
                .updateSimpleMemo(simpleMemoTypes);

        return serviceConverter.putResultFrom(dashboard);
    }

    public DashboardGetResult findDashboard(final long dashboardId, final long memberId) {
        memberAccessService.validateMember(memberId);

        final Dashboard dashboard = dashboardRepository.findDashboardById(dashboardId);

        final ChildInfo childInfo = childAccessService.findChildInfo(dashboard.getChildId());
        final AcademyInfo academyInfo = academyAccessService.findAcademyInfo(dashboard.getAcademyId());
        final LessonInfo lessonInfo = academyAccessService.findLessonInfo(dashboard.getLessonId());

        return serviceConverter.postResultFrom(dashboard, childInfo, academyInfo, lessonInfo);
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

                    return serviceConverter.postResultFrom(dashboard, childInfo, academyInfo, lessonInfo);
                })
                .toList();

        return serviceConverter.postResultFrom(results);
    }

    @Transactional
    public void deleteDashboard(final Long dashboardId, final Long memberId) {
        memberAccessService.validateMember(memberId);

        dashboardRepository.findDashboardById(dashboardId)
                .delete();
    }

    @Transactional
    public DashboardPatchResult toggleActiveOfDashboard(final long dashboardId, final long memberId) {
        memberAccessService.validateMember(memberId);

        final Dashboard dashboard = dashboardRepository.findDashboardById(dashboardId)
                .toggleActive();

        return serviceConverter.patchResultFrom(dashboard);
    }

}
