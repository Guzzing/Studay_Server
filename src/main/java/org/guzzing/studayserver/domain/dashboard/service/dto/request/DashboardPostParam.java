package org.guzzing.studayserver.domain.dashboard.service.dto.request;

import java.time.LocalDate;
import java.util.Map;
import org.guzzing.studayserver.domain.dashboard.model.dto.PaymentInfo;
import org.guzzing.studayserver.domain.dashboard.model.vo.Repeatance;
import org.guzzing.studayserver.domain.dashboard.model.vo.SimpleMemoType;
import org.guzzing.studayserver.domain.dashboard.service.vo.ScheduleInfos;

public record DashboardPostParam(
        Long academyId,
        ScheduleInfos scheduleInfos,
        Repeatance repeatance,
        Long childId,
        Long lessonId,
        PaymentInfo paymentInfo,
        LocalDate paymentDay,
        Map<SimpleMemoType, Boolean> simpleMemoTypeMap
) {

}
