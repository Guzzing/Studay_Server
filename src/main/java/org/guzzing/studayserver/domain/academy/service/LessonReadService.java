package org.guzzing.studayserver.domain.academy.service;

import java.util.List;

import org.guzzing.studayserver.domain.academy.model.Lesson;
import org.guzzing.studayserver.domain.academy.repository.lesson.LessonRepository;
import org.guzzing.studayserver.domain.academy.service.dto.result.LessonFindByIdsResults;
import org.guzzing.studayserver.domain.academy.service.dto.result.LessonFindByIdsResults.LessonFindByIdsResult;
import org.springframework.stereotype.Service;

@Service
public class LessonReadService {

    private final LessonRepository lessonRepository;

    public LessonReadService(LessonRepository lessonRepository) {
        this.lessonRepository = lessonRepository;
    }


    public LessonFindByIdsResults findByIds(List<Long> lessonIds) {
        List<Lesson> lessons = lessonRepository.findByIds(lessonIds);

        List<LessonFindByIdsResult> lessonFindByIdsResults = lessons.stream()
                .map(l -> new LessonFindByIdsResult(
                        l.getId(),
                        l.getAcademy().getAcademyName(),
                        l.getSubject()
                )).toList();

        return new LessonFindByIdsResults(lessonFindByIdsResults);
    }
}
