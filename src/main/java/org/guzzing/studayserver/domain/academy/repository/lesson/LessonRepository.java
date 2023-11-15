package org.guzzing.studayserver.domain.academy.repository.lesson;

import java.util.List;
import java.util.Optional;
import org.guzzing.studayserver.domain.academy.model.Lesson;

public interface LessonRepository {

    List<Lesson> findAllByAcademyId(Long academyId);

    Lesson save(Lesson lesson);

    void deleteAll();

    boolean existsById(final Long lessonId);

    Optional<Lesson> findById(final Long lessonId);

}
