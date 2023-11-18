package org.guzzing.studayserver.domain.academy.repository.lesson;

import java.util.List;

import org.guzzing.studayserver.domain.academy.model.Lesson;
import org.guzzing.studayserver.domain.academy.repository.dto.LessonInfoToCreateDashboard;
import org.springframework.data.repository.query.Param;

public interface LessonRepository {

    List<Lesson> findAllByAcademyId(Long academyId);

    List<LessonInfoToCreateDashboard> findAllLessonInfoByAcademyId (Long academyId);

    Lesson save(Lesson lesson);

    void deleteAll();

}
