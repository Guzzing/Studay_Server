package org.guzzing.studayserver.domain.dashboard.service.dto.request;

import java.util.Map;
import org.guzzing.studayserver.domain.dashboard.model.dto.PaymentInfo;
import org.guzzing.studayserver.domain.dashboard.model.vo.SimpleMemoType;

public record DashboardPutParam(
        long dashboardId,
        PaymentInfo paymentInfo,
        Map<SimpleMemoType, Boolean> simpleMemoTypeMap
) {

}
