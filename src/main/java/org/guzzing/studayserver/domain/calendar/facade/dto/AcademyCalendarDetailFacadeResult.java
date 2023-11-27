package org.guzzing.studayserver.domain.calendar.facade.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import org.guzzing.studayserver.domain.academy.service.dto.result.AcademyAndLessonDetailResult;
import org.guzzing.studayserver.domain.calendar.model.Periodicity;
import org.guzzing.studayserver.domain.calendar.service.dto.result.AcademyCalendarDetailResults;
import org.guzzing.studayserver.domain.child.service.result.AcademyCalendarDetailChildInfo;

public record AcademyCalendarDetailFacadeResult(
        String date,
        AcademyInfoAboutScheduleDetail academyInfoAboutScheduleDetail,
        LessonInfo lessonInfo,
        List<FacadeChildInfo> childrenInfos,
        List<String> categories
) {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd (E)", Locale.KOREAN);

    public static AcademyCalendarDetailFacadeResult from(
            AcademyAndLessonDetailResult academyAndLessonDetailResult,
            List<AcademyCalendarDetailChildInfo> childImages,
            AcademyCalendarDetailResults academyCalendarDetailResults,
            LocalDate requestedDate
    ) {
        return new AcademyCalendarDetailFacadeResult(
                requestedDate.format(formatter),
                new AcademyInfoAboutScheduleDetail(
                        academyAndLessonDetailResult.academyName(),
                        academyAndLessonDetailResult.address()
                ),
                new LessonInfo(
                        academyAndLessonDetailResult.lessonName(),
                        academyAndLessonDetailResult.capacity(),
                        academyAndLessonDetailResult.totalFee(),
                        academyCalendarDetailResults.academyCalendarDetailResults()
                                .values().stream().map(
                                        academyCalendarDetailResult ->
                                                new LessonInfo.FacadeLessonTime(
                                                        academyCalendarDetailResult.lessonStartTime(),
                                                        academyCalendarDetailResult.lessonEndTime()
                                                )
                                )
                                .toList(),
                        Periodicity.WEEKLY
                ),
                childImages.stream()
                        .map(childImage ->
                                new FacadeChildInfo(
                                        childImage.childId(),
                                        childImage.childName(),
                                        childImage.imageUrl(),
                                        academyCalendarDetailResults.academyCalendarDetailResults()
                                                .get(childImage.childId()).memo(),
                                        academyCalendarDetailResults.academyCalendarDetailResults()
                                                .get(childImage.childId()).dashboardId()
                                ))
                        .toList(),
                academyAndLessonDetailResult.categories()
        );
    }

    public record AcademyInfoAboutScheduleDetail(
            String academyName,
            String address
    ) {

    }

    public record LessonInfo(
            String lessonName,
            Integer capacity,
            Long totalFee,
            List<FacadeLessonTime> lessonTimes,
            Periodicity periodicity
    ) {

        public record FacadeLessonTime(
                LocalTime startTime,
                LocalTime endTime
        ) {

        }
    }

    public record FacadeChildInfo(
            Long childId,
            String childName,
            String imageUrl,
            String memo,
            Long dashBoardId
    ) {

    }

}
