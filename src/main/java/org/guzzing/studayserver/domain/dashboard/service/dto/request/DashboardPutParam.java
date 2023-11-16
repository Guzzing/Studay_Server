package org.guzzing.studayserver.domain.dashboard.service.dto.request;

import java.util.Map;
import org.guzzing.studayserver.domain.dashboard.model.dto.PaymentInfo;
import org.guzzing.studayserver.domain.dashboard.model.vo.SimpleMemoType;
import org.guzzing.studayserver.domain.dashboard.service.vo.ScheduleInfos;

public record DashboardPutParam(
        long dashboardId,
        long childId,
        long academyId,
        long lessonId,
        ScheduleInfos scheduleInfos,
        PaymentInfo paymentInfo,
        Map<SimpleMemoType, Boolean> simpleMemoTypeMap,
        boolean isActive,
        boolean isDeleted
) {

}