package org.guzzing.studayserver.domain.dashboard.service.dto.response;

import org.guzzing.studayserver.domain.dashboard.model.dto.PaymentInfo;
import org.guzzing.studayserver.domain.dashboard.model.vo.SimpleMemo;
import org.guzzing.studayserver.domain.dashboard.service.vo.ScheduleInfos;

public record DashboardResult(
        long dashboardId,
        long childId,
        long academyId,
        long lessonId,
        ScheduleInfos scheduleInfos,
        PaymentInfo paymentInfo,
        SimpleMemo simpleMemo,
        boolean isActive,
        boolean isDeleted
) {


}
