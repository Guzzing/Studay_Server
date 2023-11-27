package org.guzzing.studayserver.domain.academy.service.dto.result;

import org.guzzing.studayserver.domain.academy.model.Lesson;

public record AcademyAndLessonDetailResult(
        String academyName,
        String address,
        String lessonName,
        Integer capacity,
        Long totalFee
) {

    public static AcademyAndLessonDetailResult from(Lesson lesson) {
        return new AcademyAndLessonDetailResult(
                lesson.getAcademy().getAcademyName(),
                lesson.getAcademy().getFullAddress(),
                lesson.getCurriculum(),
                lesson.getCapacity(),
                lesson.getTotalFee()
        );
    }
}
