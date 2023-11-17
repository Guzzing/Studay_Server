package org.guzzing.studayserver.domain.dashboard.controller.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.List;
import org.guzzing.studayserver.domain.dashboard.controller.vo.Schedule;
import org.guzzing.studayserver.domain.dashboard.controller.vo.SimpleMemo;
import org.guzzing.studayserver.domain.dashboard.model.dto.PaymentInfo;

public record DashboardPutRequest(
        @Positive Long childId,
        @Positive Long academyId,
        @Positive Long lessonId,
        List<Schedule> schedules,
        PaymentInfo paymentInfo,
        @NotNull SimpleMemo simpleMemo,
        @NotNull boolean isActive,
        @NotNull boolean isDeleted
) {

}
