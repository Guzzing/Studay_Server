package org.guzzing.studayserver.domain.academy.repository.lesson;

import java.util.List;
import java.util.Optional;

import jakarta.persistence.EntityNotFoundException;
import org.guzzing.studayserver.domain.academy.model.Lesson;
import org.guzzing.studayserver.global.error.response.ErrorCode;
import org.springframework.data.repository.query.Param;

public interface LessonRepository {

    List<Lesson> findAllByAcademyId(Long academyId);

    Lesson save(Lesson lesson);

    void deleteAll();

    boolean existsById(final Long lessonId);

    Optional<Lesson> findById(final Long lessonId);

    Optional<Lesson> findLessonById(Long lessonId);

    default Lesson getLessonById(Long lessonId) {
        return findLessonById(lessonId).orElseThrow(() ->
                new EntityNotFoundException(ErrorCode.NOT_FOUND_ENTITY.getMessage()));
    }

}
