package org.guzzing.studayserver.domain.dashboard.facade.dto;

import org.guzzing.studayserver.domain.dashboard.facade.vo.AcademyInfo;
import org.guzzing.studayserver.domain.dashboard.facade.vo.ChildInfo;
import org.guzzing.studayserver.domain.dashboard.facade.vo.LessonInfo;
import org.guzzing.studayserver.domain.dashboard.model.dto.PaymentInfo;
import org.guzzing.studayserver.domain.dashboard.model.vo.SimpleMemo;
import org.guzzing.studayserver.domain.dashboard.service.vo.ScheduleInfos;

public record DashboardGetResult(
        long dashboardId,
        ChildInfo childInfo,
        AcademyInfo academyInfo,
        LessonInfo lessonInfo,
        ScheduleInfos scheduleInfos,
        PaymentInfo paymentInfo,
        SimpleMemo simpleMemo,
        boolean isActive,
        boolean isDeleted
) {

}
