package org.guzzing.studayserver.domain.dashboard.controller.dto.response;

import org.guzzing.studayserver.domain.dashboard.model.dto.PaymentInfo;
import org.guzzing.studayserver.domain.dashboard.model.vo.SimpleMemo;

public record DashboardPutResponse(
        long dashboardId,
        PaymentInfo paymentInfo,
        SimpleMemo simpleMemo
) {

}
