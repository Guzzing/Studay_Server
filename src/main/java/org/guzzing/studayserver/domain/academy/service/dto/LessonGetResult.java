package org.guzzing.studayserver.domain.academy.service.dto;

import org.guzzing.studayserver.domain.academy.model.Lesson;

public record LessonGetResult(
        Long lessonId,
        String subject,
        Integer capacity,
        String duration,
        Long totalFee
) {
    public static LessonGetResult of(Lesson lesson) {
        return new LessonGetResult(
                lesson.getId(),
                lesson.getSubject(),
                lesson.getCapacity(),
                lesson.getDuration(),
                lesson.getTotalFee()
        );
    }
}
