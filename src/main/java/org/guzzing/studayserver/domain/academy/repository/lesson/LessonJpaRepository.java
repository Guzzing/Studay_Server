package org.guzzing.studayserver.domain.academy.repository.lesson;

import java.util.List;
import java.util.Optional;

import org.guzzing.studayserver.domain.academy.model.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LessonJpaRepository extends JpaRepository<Lesson, Long>, LessonRepository {

    List<Lesson> findAllByAcademyId(Long academyId);


    @Query("select l from Lesson as l join fetch l.academy as ac where l.id = :lessonId")
    Optional<Lesson> findLessonById(@Param(value = "lessonId") Long lessonId);
}
