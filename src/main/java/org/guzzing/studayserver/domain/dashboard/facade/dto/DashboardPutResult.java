package org.guzzing.studayserver.domain.dashboard.facade.dto;

import java.util.Map;
import org.guzzing.studayserver.domain.dashboard.model.dto.PaymentInfo;
import org.guzzing.studayserver.domain.dashboard.model.vo.SimpleMemo;
import org.guzzing.studayserver.domain.dashboard.model.vo.SimpleMemoType;

public record DashboardPutResult(
        long dashboardId,
        PaymentInfo paymentInfo,
        SimpleMemo simpleMemo
) {

}
