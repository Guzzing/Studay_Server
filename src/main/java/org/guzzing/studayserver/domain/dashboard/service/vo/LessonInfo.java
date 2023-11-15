package org.guzzing.studayserver.domain.dashboard.service.vo;

import org.guzzing.studayserver.domain.academy.model.Lesson;

public record LessonInfo(
        long lessonId,
        String subject,
        int capacity,
        String duration,
        long totalFee
) {

    public static LessonInfo from(final Lesson entity) {
        return new LessonInfo(
                entity.getId(),
                entity.getSubject(),
                entity.getCapacity(),
                entity.getDuration(),
                entity.getTotalFee());
    }

}
