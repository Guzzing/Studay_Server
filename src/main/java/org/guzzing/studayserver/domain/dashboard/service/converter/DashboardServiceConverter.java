package org.guzzing.studayserver.domain.dashboard.service.converter;

import static org.guzzing.studayserver.domain.dashboard.model.vo.SimpleMemoType.CHEAP_FEE;
import static org.guzzing.studayserver.domain.dashboard.model.vo.SimpleMemoType.GOOD_FACILITY;
import static org.guzzing.studayserver.domain.dashboard.model.vo.SimpleMemoType.GOOD_MANAGEMENT;
import static org.guzzing.studayserver.domain.dashboard.model.vo.SimpleMemoType.KINDNESS;
import static org.guzzing.studayserver.domain.dashboard.model.vo.SimpleMemoType.LOVELY_TEACHING;
import static org.guzzing.studayserver.domain.dashboard.model.vo.SimpleMemoType.SHUTTLE_AVAILABILITY;

import java.util.List;
import java.util.Map;
import org.guzzing.studayserver.domain.dashboard.model.Dashboard;
import org.guzzing.studayserver.domain.dashboard.model.DashboardSchedule;
import org.guzzing.studayserver.domain.dashboard.model.dto.PaymentInfo;
import org.guzzing.studayserver.domain.dashboard.model.vo.FeeInfo;
import org.guzzing.studayserver.domain.dashboard.model.vo.SimpleMemo;
import org.guzzing.studayserver.domain.dashboard.service.dto.request.DashboardPostParam;
import org.guzzing.studayserver.domain.dashboard.service.dto.response.DashboardResult;
import org.guzzing.studayserver.domain.dashboard.service.vo.ScheduleInfo;
import org.guzzing.studayserver.domain.dashboard.service.vo.ScheduleInfos;
import org.springframework.stereotype.Component;

@Component
public class DashboardServiceConverter {

    public Dashboard to(final DashboardPostParam param) {
        return new Dashboard(
                param.childId(),
                param.academyId(),
                param.lessonId(),
                convertToDashboardSchedules(param.scheduleInfos()),
                convertToFeeInfo(param.paymentInfo()),
                convertToSimpleMemoMap(param.simpleMemo()),
                false,
                false);
    }

    public DashboardResult from(final Dashboard entity) {
        return new DashboardResult(
                entity.getId(),
                entity.getChildId(),
                entity.getAcademyId(),
                entity.getLessonId(),
                convertToScheduleInfos(entity.getDashboardSchedules()),
                convertToPaymentInfo(entity.getFeeInfo()),
                convertToSimpleMemo(entity.getSimpleMemo()),
                entity.isActive(),
                entity.isDeleted());
    }

    public FeeInfo convertToFeeInfo(final PaymentInfo paymentInfo) {
        return new FeeInfo(
                paymentInfo.educationFee(),
                paymentInfo.bookFee(),
                paymentInfo.shuttleFee(),
                paymentInfo.etcFee(),
                paymentInfo.paymentDay());
    }

    public Map<String, Boolean> convertToSimpleMemoMap(final SimpleMemo simpleMemo) {
        return Map.of(
                KINDNESS.getType(), simpleMemo.kindness(),
                GOOD_FACILITY.getType(), simpleMemo.goodFacility(),
                CHEAP_FEE.getType(), simpleMemo.cheapFee(),
                GOOD_MANAGEMENT.getType(), simpleMemo.goodManagement(),
                LOVELY_TEACHING.getType(), simpleMemo.lovelyTeaching(),
                SHUTTLE_AVAILABILITY.getType(), simpleMemo.shuttleAvailability());
    }

    private List<DashboardSchedule> convertToDashboardSchedules(final ScheduleInfos scheduleInfos) {
        return scheduleInfos.schedules()
                .stream()
                .map(scheduleInfo -> new DashboardSchedule(
                        scheduleInfo.dayOfWeek(),
                        scheduleInfo.startTime(),
                        scheduleInfo.endTime(),
                        scheduleInfo.repeatance()
                ))
                .toList();
    }

    private ScheduleInfos convertToScheduleInfos(
            final List<DashboardSchedule> dashboardSchedules
    ) {
        final List<ScheduleInfo> scheduleInfos = dashboardSchedules.stream()
                .map(dashboardSchedule -> new ScheduleInfo(
                        dashboardSchedule.getDayOfWeek(),
                        dashboardSchedule.getStartTime(),
                        dashboardSchedule.getEndTime(),
                        dashboardSchedule.getRepeatance()))
                .toList();

        return new ScheduleInfos(scheduleInfos);
    }

    private PaymentInfo convertToPaymentInfo(final FeeInfo feeInfo) {
        return new PaymentInfo(
                feeInfo.getEducationFee(),
                feeInfo.getBookFee(),
                feeInfo.getShuttleFee(),
                feeInfo.getEtcFee(),
                feeInfo.getPaymentDay());
    }

    private SimpleMemo convertToSimpleMemo(final Map<String, Boolean> map) {
        return new SimpleMemo(
                map.get(KINDNESS.getType()),
                map.get(GOOD_FACILITY.getType()),
                map.get(CHEAP_FEE.getType()),
                map.get(GOOD_MANAGEMENT.getType()),
                map.get(LOVELY_TEACHING.getType()),
                map.get(SHUTTLE_AVAILABILITY.getType()));
    }

}
