package org.guzzing.studayserver.domain.calendar.facade.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import org.guzzing.studayserver.domain.academy.service.dto.result.AcademyAndLessonDetailResult;
import org.guzzing.studayserver.domain.calendar.model.Periodicity;
import org.guzzing.studayserver.domain.calendar.service.dto.result.AcademyCalendarDetailResult;
import org.guzzing.studayserver.domain.child.service.result.AcademyCalendarDetailChildInfo;

public record AcademyCalendarDetailFacadeResult(
        String date,
        AcademyInfoAboutScheduleDetail academyInfoAboutScheduleDetail,
        LessonInfo lessonInfo,
        FacadeChildInfo childrenInfo,
        List<String> categories
) {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd (E)", Locale.KOREAN);
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public static AcademyCalendarDetailFacadeResult from(
            AcademyAndLessonDetailResult academyAndLessonDetailResult,
            AcademyCalendarDetailChildInfo childImage,
            AcademyCalendarDetailResult academyCalendarDetailResult,
            LocalDate requestedDate
    ) {
        return new AcademyCalendarDetailFacadeResult(
                requestedDate.format(DATE_FORMATTER),
                new AcademyInfoAboutScheduleDetail(
                        academyAndLessonDetailResult.academyName(),
                        academyAndLessonDetailResult.address()
                ),
                new LessonInfo(
                        academyAndLessonDetailResult.lessonName(),
                        academyAndLessonDetailResult.capacity(),
                        academyAndLessonDetailResult.totalFee(),
                        new LessonInfo.FacadeLessonTime(
                                academyCalendarDetailResult.lessonStartTime().format(TIME_FORMATTER),
                                academyCalendarDetailResult.lessonEndTime().format(TIME_FORMATTER)),
                        Periodicity.WEEKLY
                ),
                new FacadeChildInfo(
                        childImage.childId(),
                        childImage.childName(),
                        childImage.imageUrl(),
                        academyCalendarDetailResult.memo(),
                        academyCalendarDetailResult.dashboardId()
                ),
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
            FacadeLessonTime lessonTimes,
            Periodicity periodicity
    ) {

        public record FacadeLessonTime(
                String startTime,
                String endTime
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
