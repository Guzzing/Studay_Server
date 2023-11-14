package org.guzzing.studayserver.domain.dashboard.service.dto.response;

import java.util.Map;
import org.guzzing.studayserver.domain.dashboard.model.dto.PaymentInfo;
import org.guzzing.studayserver.domain.dashboard.model.vo.SimpleMemoType;
import org.guzzing.studayserver.domain.dashboard.service.vo.AcademyInfo;
import org.guzzing.studayserver.domain.dashboard.service.vo.ChildInfo;
import org.guzzing.studayserver.domain.dashboard.service.vo.LessonInfo;
import org.guzzing.studayserver.domain.dashboard.service.vo.ScheduleInfos;

public record DashboardGetResult(
        long dashboardId,
        ChildInfo childInfo,
        AcademyInfo academyInfo,
        LessonInfo lessonInfo,
        ScheduleInfos scheduleInfos,
        PaymentInfo paymentInfo,
        Map<SimpleMemoType, Boolean> simpleMemoTypeMap,
        boolean isActive,
        boolean isDeleted
) {

}
