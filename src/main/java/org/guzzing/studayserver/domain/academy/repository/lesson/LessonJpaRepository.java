package org.guzzing.studayserver.domain.academy.repository.lesson;

import java.util.List;

import org.guzzing.studayserver.domain.academy.model.Lesson;
import org.guzzing.studayserver.domain.academy.repository.dto.LessonInfoToCreateDashboard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LessonJpaRepository extends JpaRepository<Lesson, Long>, LessonRepository {

    List<Lesson> findAllByAcademyId(Long academyId);

    List<LessonInfoToCreateDashboard> findAllLessonInfoByAcademyId (Long academyId);

}
