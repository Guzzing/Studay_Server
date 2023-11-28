package org.guzzing.studayserver.domain.academy.repository.lesson;

import jakarta.persistence.EntityNotFoundException;

import java.util.List;
import java.util.Optional;

import org.guzzing.studayserver.domain.academy.model.Lesson;
import org.guzzing.studayserver.domain.academy.repository.dto.LessonInfoToCreateDashboard;
import org.guzzing.studayserver.global.error.response.ErrorCode;


public interface LessonRepository {

    List<Lesson> findAllByAcademyId(Long academyId);

    List<LessonInfoToCreateDashboard> findAllLessonInfoByAcademyId(Long academyId);

    Lesson save(Lesson lesson);

    void deleteAll();

    boolean existsById(final Long lessonId);

    Optional<Lesson> findById(final Long lessonId);

    Optional<Lesson> findLessonById(Long lessonId);

    default Lesson getLessonById(Long lessonId) {
        return findLessonById(lessonId).orElseThrow(() ->
                new EntityNotFoundException(ErrorCode.NOT_FOUND_ENTITY.getMessage()));
    }

    List<Lesson> findByIds(List<Long> lessonIds);

}
