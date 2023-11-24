package org.guzzing.studayserver.domain.academy.repository.lesson;

import java.util.List;
import java.util.Optional;

import org.guzzing.studayserver.domain.academy.model.Lesson;
import org.guzzing.studayserver.domain.academy.repository.dto.LessonInfoToCreateDashboard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LessonJpaRepository extends JpaRepository<Lesson, Long>, LessonRepository {

    List<Lesson> findAllByAcademyId(Long academyId);

    @Query("select l from Lesson as l join fetch l.academy as ac where l.id = :lessonId")
    Optional<Lesson> findLessonById(@Param(value = "lessonId") Long lessonId);

    List<LessonInfoToCreateDashboard> findAllLessonInfoByAcademyId(Long academyId);


    @Query("SELECT ls FROM Lesson AS ls "
            + "JOIN FETCH ls.academy aca "
            + "WHERE ls.id IN :lessonIds")
    List<Lesson> findByIds(List<Long> lessonIds);

}
