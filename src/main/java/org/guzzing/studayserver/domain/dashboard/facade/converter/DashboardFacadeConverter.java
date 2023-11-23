package org.guzzing.studayserver.domain.dashboard.facade.converter;

import java.util.List;
import org.guzzing.studayserver.domain.dashboard.facade.dto.DashboardGetResult;
import org.guzzing.studayserver.domain.dashboard.facade.dto.DashboardGetResults;
import org.guzzing.studayserver.domain.dashboard.facade.dto.DashboardPatchResult;
import org.guzzing.studayserver.domain.dashboard.facade.dto.DashboardPostResult;
import org.guzzing.studayserver.domain.dashboard.facade.dto.DashboardPutResult;
import org.guzzing.studayserver.domain.dashboard.facade.vo.AcademyInfo;
import org.guzzing.studayserver.domain.dashboard.facade.vo.ChildInfo;
import org.guzzing.studayserver.domain.dashboard.facade.vo.LessonInfo;
import org.guzzing.studayserver.domain.dashboard.service.dto.response.DashboardResult;
import org.springframework.stereotype.Component;

@Component
public class DashboardFacadeConverter {

    public DashboardPostResult postFromResult(final DashboardResult result) {
        return new DashboardPostResult(
                result.dashboardId(),
                result.childId(),
                result.academyId(),
                result.lessonId(),
                result.scheduleInfos(),
                result.paymentInfo(),
                result.simpleMemoTypeMap(),
                result.isActive(),
                result.isDeleted());
    }

    public DashboardPutResult putFromResult(final DashboardResult result) {
        return new DashboardPutResult(
                result.dashboardId(),
                result.paymentInfo(),
                result.simpleMemoTypeMap());
    }

    public DashboardPatchResult patchFromResult(final DashboardResult result) {
        return new DashboardPatchResult(result.dashboardId(), result.isActive());
    }

    public DashboardGetResult getFromResult(
            final DashboardResult result,
            final ChildInfo childInfo,
            final AcademyInfo academyInfo,
            final LessonInfo lessonInfo
    ) {
        return new DashboardGetResult(
                result.dashboardId(),
                childInfo,
                academyInfo,
                lessonInfo,
                result.scheduleInfos(),
                result.paymentInfo(),
                result.simpleMemoTypeMap(),
                result.isActive(),
                result.isDeleted());
    }

    public DashboardGetResults getFromResults(
            List<DashboardGetResult> getResults
    ) {
        return new DashboardGetResults(getResults);
    }

}
