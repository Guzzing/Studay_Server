package org.guzzing.studayserver.domain.dashboard.facade.dto;

import org.guzzing.studayserver.domain.dashboard.model.dto.PaymentInfo;
import org.guzzing.studayserver.domain.dashboard.model.vo.SimpleMemo;

public record DashboardPutResult(
        long dashboardId,
        PaymentInfo paymentInfo,
        SimpleMemo simpleMemo
) {

}
