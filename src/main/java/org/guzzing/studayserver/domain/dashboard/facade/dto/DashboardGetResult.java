package org.guzzing.studayserver.domain.dashboard.facade.dto;

import java.util.Map;
import org.guzzing.studayserver.domain.dashboard.facade.vo.AcademyInfo;
import org.guzzing.studayserver.domain.dashboard.facade.vo.ChildInfo;
import org.guzzing.studayserver.domain.dashboard.facade.vo.LessonInfo;
import org.guzzing.studayserver.domain.dashboard.model.dto.PaymentInfo;
import org.guzzing.studayserver.domain.dashboard.model.vo.SimpleMemoType;
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
