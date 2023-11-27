package org.guzzing.studayserver.domain.dashboard.controller.dto.request;

import jakarta.validation.constraints.NotNull;
import org.guzzing.studayserver.domain.dashboard.model.vo.SimpleMemo;
import org.guzzing.studayserver.domain.dashboard.model.dto.PaymentInfo;

public record DashboardPutRequest(
        PaymentInfo paymentInfo,
        @NotNull SimpleMemo simpleMemo
) {

}
