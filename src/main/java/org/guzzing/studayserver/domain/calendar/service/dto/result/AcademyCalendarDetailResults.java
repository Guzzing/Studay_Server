package org.guzzing.studayserver.domain.calendar.service.dto.result;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.guzzing.studayserver.domain.calendar.repository.dto.AcademyCalenderDetailInfo;

public record AcademyCalendarDetailResults(
        Map<Long, AcademyCalendarDetailResult> academyCalendarDetailResults

) {

    public static AcademyCalendarDetailResults from(List<AcademyCalenderDetailInfo> academyCalenderDetailInfos) {
        Map<Long, AcademyCalendarDetailResult> academyCalendarDetailResults = new HashMap<>();
        academyCalenderDetailInfos.
                forEach(academyCalenderDetailInfo ->
                        academyCalendarDetailResults.put(academyCalenderDetailInfo.getChildId()
                                , AcademyCalendarDetailResult.from(academyCalenderDetailInfo))

                );
        return new AcademyCalendarDetailResults(academyCalendarDetailResults);
    }

    public record AcademyCalendarDetailResult(
            Long dashboardId,
            LocalTime lessonStartTime,
            LocalTime lessonEndTime,
            String memo
    ) {

        public static AcademyCalendarDetailResult from(AcademyCalenderDetailInfo academyCalenderDetailInfo) {
            return new AcademyCalendarDetailResult(
                    academyCalenderDetailInfo.getDashboardId(),
                    academyCalenderDetailInfo.getLessonStartTime(),
                    academyCalenderDetailInfo.getLessonEndTime(),
                    academyCalenderDetailInfo.getMemo()
            );
        }
    }

}
