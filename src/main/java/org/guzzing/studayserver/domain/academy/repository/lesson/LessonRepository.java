package org.guzzing.studayserver.domain.academy.repository.lesson;

import java.util.List;
import org.guzzing.studayserver.domain.academy.model.Lesson;

public interface LessonRepository {

    List<Lesson> findAllByAcademyId(Long academyId);

    Lesson save(Lesson lesson);

    void deleteAll();

}
