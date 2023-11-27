package org.guzzing.studayserver.domain.calendar.controller.dto.response;

import java.time.LocalTime;
import java.util.List;
import org.guzzing.studayserver.domain.calendar.facade.dto.AcademyCalendarDetailFacadeResult;
import org.guzzing.studayserver.domain.calendar.model.Periodicity;

public record AcademyCalendarDetailResponse(
        String date,
        AcademyInfoResponseAboutScheduleDetail academyInfo,
        LessonInfoResponse lessonInfo,
        List<ChildInfoResponse> childrenInfos
) {

    public static AcademyCalendarDetailResponse from(
            AcademyCalendarDetailFacadeResult academyCalendarDetailFacadeResult
    ) {
        return new AcademyCalendarDetailResponse(
                academyCalendarDetailFacadeResult.date(),
                AcademyInfoResponseAboutScheduleDetail.from(
                        academyCalendarDetailFacadeResult.academyInfoAboutScheduleDetail()),
                LessonInfoResponse.from(academyCalendarDetailFacadeResult.lessonInfo()),
                academyCalendarDetailFacadeResult.childrenInfos().stream()
                        .map(childInfo -> new ChildInfoResponse(
                                childInfo.childId(),
                                childInfo.childName(),
                                childInfo.imageUrl(),
                                childInfo.memo(),
                                childInfo.dashBoardId()))
                        .toList()
        );
    }

    public record AcademyInfoResponseAboutScheduleDetail(
            String academyName,
            String address
    ) {

        public static AcademyInfoResponseAboutScheduleDetail from(
                AcademyCalendarDetailFacadeResult.AcademyInfoAboutScheduleDetail academyInfo) {
            return new AcademyInfoResponseAboutScheduleDetail(
                    academyInfo.academyName(),
                    academyInfo.address()
            );
        }
    }

    public record LessonInfoResponse(
            String lessonName,
            Integer capacity,
            Long totalFee,
            List<LessonTimeResponse> lessonTimes,
            Periodicity periodicity
    ) {

        static LessonInfoResponse from(AcademyCalendarDetailFacadeResult.LessonInfo lessonInfo) {
            return new LessonInfoResponse(
                    lessonInfo.lessonName(),
                    lessonInfo.capacity(),
                    lessonInfo.totalFee(),
                    lessonInfo.lessonTimes().stream()
                            .map(lessonTime -> new LessonTimeResponse(
                                    lessonTime.startTime(), lessonTime.endTime()))
                            .toList(),
                    lessonInfo.periodicity()
            );
        }

        public record LessonTimeResponse(
                LocalTime startTime,
                LocalTime endTime
        ) {

        }
    }

    public record ChildInfoResponse(
            Long childId,
            String childName,
            String imageUrl,
            String memo,
            Long dashBoardId
    ) {

    }

}
