package org.guzzing.studayserver.domain.dashboard.controller.dto.response;

import org.guzzing.studayserver.domain.dashboard.model.vo.SimpleMemo;
import org.guzzing.studayserver.domain.dashboard.model.dto.PaymentInfo;

public record DashboardPutResponse(
        long dashboardId,
        PaymentInfo paymentInfo,
        SimpleMemo simpleMemo
) {

}
