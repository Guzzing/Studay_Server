package org.guzzing.studayserver.domain.calendar.controller.dto.response;

import java.util.List;
import org.guzzing.studayserver.domain.calendar.facade.dto.AcademyCalendarDetailFacadeResult;
import org.guzzing.studayserver.domain.calendar.model.Periodicity;

public record AcademyCalendarDetailResponse(
        String date,
        AcademyInfoResponseAboutScheduleDetail academyInfo,
        LessonInfoResponse lessonInfo,
        ChildInfoResponse childrenInfo,
        List<String> categories
) {

    public static AcademyCalendarDetailResponse from(
            AcademyCalendarDetailFacadeResult academyCalendarDetailFacadeResult
    ) {
        return new AcademyCalendarDetailResponse(
                academyCalendarDetailFacadeResult.date(),
                AcademyInfoResponseAboutScheduleDetail.from(
                        academyCalendarDetailFacadeResult.academyInfoAboutScheduleDetail()),
                LessonInfoResponse.from(academyCalendarDetailFacadeResult.lessonInfo()),
               new ChildInfoResponse(
                       academyCalendarDetailFacadeResult.childrenInfo().childId(),
                               academyCalendarDetailFacadeResult.childrenInfo().childName(),
                       academyCalendarDetailFacadeResult.childrenInfo().imageUrl(),
                       academyCalendarDetailFacadeResult.childrenInfo().memo(),
                       academyCalendarDetailFacadeResult.childrenInfo().dashBoardId()),
                academyCalendarDetailFacadeResult.categories()
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
            LessonTimeResponse lessonTimes,
            Periodicity periodicity
    ) {

        static LessonInfoResponse from(AcademyCalendarDetailFacadeResult.LessonInfo lessonInfo) {
            return new LessonInfoResponse(
                    lessonInfo.lessonName(),
                    lessonInfo.capacity(),
                    lessonInfo.totalFee(),
                     new LessonTimeResponse(
                             lessonInfo.lessonTimes().startTime(),
                             lessonInfo.lessonTimes().endTime()),
                    Periodicity.WEEKLY);
        }

        public record LessonTimeResponse(
                String startTime,
                String endTime
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
