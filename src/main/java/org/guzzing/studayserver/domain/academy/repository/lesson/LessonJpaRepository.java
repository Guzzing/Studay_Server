package org.guzzing.studayserver.domain.academy.repository.lesson;

import java.util.List;
import org.guzzing.studayserver.domain.academy.model.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LessonJpaRepository extends JpaRepository<Lesson, Long>, LessonRepository {

    List<Lesson> findAllByAcademyId(Long academyId);

}
