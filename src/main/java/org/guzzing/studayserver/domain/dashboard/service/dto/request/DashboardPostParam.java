package org.guzzing.studayserver.domain.dashboard.service.dto.request;

import java.util.Map;
import org.guzzing.studayserver.domain.dashboard.model.dto.PaymentInfo;
import org.guzzing.studayserver.domain.dashboard.model.vo.SimpleMemoType;
import org.guzzing.studayserver.domain.dashboard.service.vo.ScheduleInfos;

public record DashboardPostParam(
        Long childId,
        Long academyId,
        Long lessonId,
        ScheduleInfos scheduleInfos,
        PaymentInfo paymentInfo,
        Map<SimpleMemoType, Boolean> simpleMemoTypeMap
) {

}
