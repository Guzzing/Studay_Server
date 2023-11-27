package org.guzzing.studayserver.domain.dashboard.service.dto.request;

import org.guzzing.studayserver.domain.dashboard.model.vo.SimpleMemo;
import org.guzzing.studayserver.domain.dashboard.model.dto.PaymentInfo;
import org.guzzing.studayserver.domain.dashboard.service.vo.ScheduleInfos;

public record DashboardPostParam(
        Long childId,
        Long academyId,
        Long lessonId,
        ScheduleInfos scheduleInfos,
        PaymentInfo paymentInfo,
        SimpleMemo simpleMemo
) {

}
