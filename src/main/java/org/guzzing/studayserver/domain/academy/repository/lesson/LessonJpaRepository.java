package org.guzzing.studayserver.domain.academy.repository.lesson;

import org.guzzing.studayserver.domain.academy.model.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LessonJpaRepository extends JpaRepository<Lesson, Long> , LessonRepository{

    List<Lesson> findAllByAcademyId(Long academyId);

}
