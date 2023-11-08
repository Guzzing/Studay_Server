package org.guzzing.studayserver.domain.dashboard.controller.dto.request;

import java.time.LocalDate;
import org.guzzing.studayserver.domain.dashboard.controller.vo.DashboardSchedules;
import org.guzzing.studayserver.domain.dashboard.controller.vo.SimpleMemo;
import org.guzzing.studayserver.domain.dashboard.model.dto.PaymentInfo;

public record DashboardPostRequest(
        Long academyId,
        DashboardSchedules dashboardSchedules,
        String repeatance,
        Long childId,
        Long lessonId,
        PaymentInfo paymentInfo,
        LocalDate paymentDay,
        SimpleMemo simpleMemo
) {

}
