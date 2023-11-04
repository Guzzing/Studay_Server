package org.guzzing.studayserver.domain.academy.service.dto.result;

import org.guzzing.studayserver.domain.academy.model.Lesson;

public record LessonGetResult(
        Long lessonId,
        String subject,
        Integer capacity,
        String duration,
        Long totalFee
) {
    public static LessonGetResult from(Lesson lesson) {
        return new LessonGetResult(
                lesson.getId(),
                lesson.getSubject(),
                lesson.getCapacity(),
                lesson.getDuration(),
                lesson.getTotalFee()
        );
    }
}
