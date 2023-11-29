package org.guzzing.studayserver.domain.dashboard.service.dto.request;

import org.guzzing.studayserver.domain.dashboard.model.dto.PaymentInfo;
import org.guzzing.studayserver.domain.dashboard.model.vo.SimpleMemo;

public record DashboardPutParam(
        long dashboardId,
        PaymentInfo paymentInfo,
        SimpleMemo simpleMemo
) {

}
