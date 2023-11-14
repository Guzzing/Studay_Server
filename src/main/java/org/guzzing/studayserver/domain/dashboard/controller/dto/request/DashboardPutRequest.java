package org.guzzing.studayserver.domain.dashboard.controller.dto.request;

import java.util.List;
import org.guzzing.studayserver.domain.dashboard.controller.vo.Schedule;
import org.guzzing.studayserver.domain.dashboard.controller.vo.SimpleMemo;
import org.guzzing.studayserver.domain.dashboard.model.dto.PaymentInfo;

public record DashboardPutRequest(
        Long childId,
        Long academyId,
        Long lessonId,
        List<Schedule> schedules,
        PaymentInfo paymentInfo,
        SimpleMemo simpleMemo,
        boolean isActive,
        boolean isDeleted
) {

}
