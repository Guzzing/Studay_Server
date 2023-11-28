package org.guzzing.studayserver.domain.dashboard.controller.dto.response;

import java.util.List;
import org.guzzing.studayserver.domain.dashboard.controller.vo.Schedule;
import org.guzzing.studayserver.domain.dashboard.facade.vo.AcademyInfo;
import org.guzzing.studayserver.domain.dashboard.facade.vo.ChildInfo;
import org.guzzing.studayserver.domain.dashboard.facade.vo.LessonInfo;
import org.guzzing.studayserver.domain.dashboard.model.dto.PaymentInfo;
import org.guzzing.studayserver.domain.dashboard.model.vo.SimpleMemo;

public record DashboardGetResponse(
        Long dashboardId,
        ChildInfo childInfo,
        AcademyInfo academyInfo,
        LessonInfo lessonInfo,
        List<Schedule> schedules,
        PaymentInfo paymentInfo,
        SimpleMemo simpleMemo,
        boolean isActive,
        boolean isDeleted
) {

}
