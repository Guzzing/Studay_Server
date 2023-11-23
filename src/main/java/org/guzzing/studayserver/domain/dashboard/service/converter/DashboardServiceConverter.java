package org.guzzing.studayserver.domain.dashboard.service.converter;

import java.util.List;
import java.util.Map;
import org.guzzing.studayserver.domain.dashboard.model.Dashboard;
import org.guzzing.studayserver.domain.dashboard.model.DashboardSchedule;
import org.guzzing.studayserver.domain.dashboard.model.dto.PaymentInfo;
import org.guzzing.studayserver.domain.dashboard.model.vo.FeeInfo;
import org.guzzing.studayserver.domain.dashboard.model.vo.SimpleMemoType;
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
                convertToSelectedSimpleMemoTypes(param.simpleMemoTypeMap()),
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
                convertToSimpleMemoTypeMap(entity.getSimpleMemoTypes()),
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

    public List<SimpleMemoType> convertToSelectedSimpleMemoTypes(final Map<SimpleMemoType, Boolean> map) {
        return SimpleMemoType.getSelectedSimpleMemos(map);
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

    private Map<SimpleMemoType, Boolean> convertToSimpleMemoTypeMap(final List<SimpleMemoType> simpleMemoTypes) {
        return SimpleMemoType.convertToSimpleMemoMap(simpleMemoTypes);
    }

}
