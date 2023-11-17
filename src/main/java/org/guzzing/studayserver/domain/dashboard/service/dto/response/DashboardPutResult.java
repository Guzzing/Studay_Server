package org.guzzing.studayserver.domain.dashboard.service.dto.response;

import java.util.Map;
import org.guzzing.studayserver.domain.dashboard.model.dto.PaymentInfo;
import org.guzzing.studayserver.domain.dashboard.model.vo.SimpleMemoType;

public record DashboardPutResult(
        long dashboardId,
        PaymentInfo paymentInfo,
        Map<SimpleMemoType, Boolean> simpleMemoTypeMap
) {

}
