package org.guzzing.studayserver.domain.academy.repository.lesson;

import org.guzzing.studayserver.domain.academy.model.Lesson;

import java.util.List;

public interface LessonRepository {

    List<Lesson> findAllByAcademyId(Long academyId);

    Lesson save(Lesson lesson);

}