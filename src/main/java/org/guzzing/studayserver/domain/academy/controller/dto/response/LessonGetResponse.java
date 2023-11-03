package org.guzzing.studayserver.domain.academy.controller.dto.response;

import org.guzzing.studayserver.domain.academy.service.dto.LessonGetResult;

public record LessonGetResponse(
        Long lessonId,
        String subject,
        Integer capacity,
        String duration,
        Long totalFee
) {
    public static LessonGetResponse from(LessonGetResult lesson) {
        return new LessonGetResponse(
                lesson.lessonId(),
                lesson.subject(),
                lesson.capacity(),
                lesson.duration(),
                lesson.totalFee()
        );
    }
}
