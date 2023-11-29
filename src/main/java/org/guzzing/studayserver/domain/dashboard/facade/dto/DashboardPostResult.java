package org.guzzing.studayserver.domain.dashboard.facade.dto;

import org.guzzing.studayserver.domain.dashboard.model.dto.PaymentInfo;
import org.guzzing.studayserver.domain.dashboard.model.vo.SimpleMemo;
import org.guzzing.studayserver.domain.dashboard.service.vo.ScheduleInfos;

public record DashboardPostResult(
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
