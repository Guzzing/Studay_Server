package org.guzzing.studayserver.domain.dashboard.controller.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.List;
import org.guzzing.studayserver.domain.dashboard.controller.vo.Schedule;
import org.guzzing.studayserver.domain.dashboard.model.dto.PaymentInfo;
import org.guzzing.studayserver.domain.dashboard.model.vo.SimpleMemo;

public record DashboardPostRequest(
        @Positive Long childId,
        @Positive Long academyId,
        @Positive Long lessonId,
        @NotNull List<Schedule> schedules,
        @NotNull PaymentInfo paymentInfo,
        @NotNull SimpleMemo simpleMemo
) {

}
