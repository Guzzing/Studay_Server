package org.guzzing.studayserver.domain.dashboard.controller.converter;

import static org.guzzing.studayserver.domain.dashboard.model.vo.SimpleMemoType.CHEAP_FEE;
import static org.guzzing.studayserver.domain.dashboard.model.vo.SimpleMemoType.GOOD_FACILITY;
import static org.guzzing.studayserver.domain.dashboard.model.vo.SimpleMemoType.GOOD_MANAGEMENT;
import static org.guzzing.studayserver.domain.dashboard.model.vo.SimpleMemoType.KINDNESS;
import static org.guzzing.studayserver.domain.dashboard.model.vo.SimpleMemoType.LOVELY_TEACHING;
import static org.guzzing.studayserver.domain.dashboard.model.vo.SimpleMemoType.SHUTTLE_AVAILABILITY;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.guzzing.studayserver.domain.dashboard.controller.dto.request.DashboardPostRequest;
import org.guzzing.studayserver.domain.dashboard.controller.dto.response.DashboardPostResponse;
import org.guzzing.studayserver.domain.dashboard.controller.vo.DashboardSchedules;
import org.guzzing.studayserver.domain.dashboard.controller.vo.SimpleMemo;
import org.guzzing.studayserver.domain.dashboard.model.vo.DayOfWeek;
import org.guzzing.studayserver.domain.dashboard.model.vo.Repeatance;
import org.guzzing.studayserver.domain.dashboard.model.vo.SimpleMemoType;
import org.guzzing.studayserver.domain.dashboard.service.dto.request.DashboardPostParam;
import org.guzzing.studayserver.domain.dashboard.service.dto.response.DashboardResult;
import org.guzzing.studayserver.domain.dashboard.service.vo.ScheduleInfo;
import org.guzzing.studayserver.domain.dashboard.service.vo.ScheduleInfos;
import org.springframework.stereotype.Component;

@Component
public class DashboardControllerConverter {

    public DashboardPostParam to(final DashboardPostRequest request) {
        Repeatance repeatance = convertToRepeatance(request.repeatance());

        return new DashboardPostParam(
                request.academyId(),
                convertToScheduleInfos(request.dashboardSchedules(), repeatance),
                repeatance,
                request.childId(),
                request.lessonId(),
                request.paymentInfo(),
                request.paymentDay(),
                convertToSimpleMemoTypeMap(request.simpleMemo()));
    }

    public DashboardPostResponse from(final DashboardResult result) {
        return new DashboardPostResponse(
                result.dashboardId(),
                result.childId(),
                result.academyId(),
                result.lessonId());
    }

    private ScheduleInfos convertToScheduleInfos(final DashboardSchedules dashboardSchedules,
            final Repeatance repeatance) {
        List<ScheduleInfo> infos = dashboardSchedules.schedules().stream()
                .map(schedule -> new ScheduleInfo(
                        DayOfWeek.of(schedule.dayOfWeek()),
                        schedule.startTime(),
                        schedule.endTime(),
                        repeatance))
                .toList();

        return new ScheduleInfos(infos);
    }

    private Repeatance convertToRepeatance(final String repeatance) {
        return Repeatance.of(repeatance);
    }

    private Map<SimpleMemoType, Boolean> convertToSimpleMemoTypeMap(final SimpleMemo simpleMemo) {
        Map<SimpleMemoType, Boolean> map = new ConcurrentHashMap<>();

        map.put(KINDNESS, simpleMemo.kindness());
        map.put(GOOD_FACILITY, simpleMemo.goodFacility());
        map.put(CHEAP_FEE, simpleMemo.cheapFee());
        map.put(GOOD_MANAGEMENT, simpleMemo.goodManagement());
        map.put(LOVELY_TEACHING, simpleMemo.lovelyTeaching());
        map.put(SHUTTLE_AVAILABILITY, simpleMemo.shuttleAvailability());

        return map;
    }

}
