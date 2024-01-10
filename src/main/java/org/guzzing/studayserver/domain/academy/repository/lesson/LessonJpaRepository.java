package org.guzzing.studayserver.domain.academy.repository.lesson;

import java.util.List;
import java.util.Optional;
import org.guzzing.studayserver.domain.academy.model.Lesson;
import org.guzzing.studayserver.domain.academy.repository.dto.LessonInfoToCreateDashboard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LessonJpaRepository extends JpaRepository<Lesson, Long> {

    @Query("select l from Lesson as l join fetch l.academy as ac where l.academy.id =:academyId")
    List<Lesson> findAllByAcademyId(@Param("academyId") Long academyId);

    @Query("select l from Lesson as l join fetch l.academy as ac where l.id = :lessonId")
    Optional<Lesson> findLessonById(@Param(value = "lessonId") Long lessonId);

    @Query("select l.id as id, l.curriculum as curriculum from Lesson as l where l.academy.id =:academyId")
    List<LessonInfoToCreateDashboard> findAllLessonInfoByAcademyId(@Param("academyId") Long academyId);

    @Query("SELECT ls FROM Lesson AS ls "
            + "JOIN FETCH ls.academy aca "
            + "WHERE ls.id IN :lessonIds")
    List<Lesson> findByIds(List<Long> lessonIds);

}
