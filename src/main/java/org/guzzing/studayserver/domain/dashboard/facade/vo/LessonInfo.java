package org.guzzing.studayserver.domain.dashboard.facade.vo;

import org.guzzing.studayserver.domain.academy.model.Lesson;

public record LessonInfo(
        long lessonId,
        String curriculum,
        int capacity,
        String duration,
        long totalFee
) {

    public static LessonInfo from(final Lesson entity) {
        return new LessonInfo(
                entity.getId(),
                entity.getCurriculum(),
                entity.getCapacity(),
                entity.getDuration(),
                entity.getTotalFee());
    }

}
