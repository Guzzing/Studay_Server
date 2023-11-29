package org.guzzing.studayserver.domain.dashboard.controller.dto.request;

import jakarta.validation.constraints.NotNull;
import org.guzzing.studayserver.domain.dashboard.model.dto.PaymentInfo;
import org.guzzing.studayserver.domain.dashboard.model.vo.SimpleMemo;

public record DashboardPutRequest(
        PaymentInfo paymentInfo,
        @NotNull SimpleMemo simpleMemo
) {

}
