package org.guzzing.studayserver.domain.academy.repository.lesson;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import org.guzzing.studayserver.domain.academy.model.Lesson;
import org.guzzing.studayserver.domain.academy.repository.dto.LessonInfoToCreateDashboard;
import org.guzzing.studayserver.global.error.response.ErrorCode;
import org.springframework.stereotype.Repository;

@Repository
public class LessonRepositoryImpl implements LessonRepository {

    private final LessonJpaRepository lessonJpaRepository;

    public LessonRepositoryImpl(LessonJpaRepository lessonJpaRepository) {
        this.lessonJpaRepository = lessonJpaRepository;
    }

    @Override
    public List<Lesson> findAllByAcademyId(Long academyId) {
        return lessonJpaRepository.findAllByAcademyId(academyId);
    }

    @Override
    public List<LessonInfoToCreateDashboard> findAllLessonInfoByAcademyId(Long academyId) {
        return lessonJpaRepository.findAllLessonInfoByAcademyId(academyId);
    }

    @Override
    public Lesson save(Lesson lesson) {
        return lessonJpaRepository.save(lesson);
    }

    @Override
    public void deleteAll() {
        lessonJpaRepository.deleteAll();
    }

    @Override
    public boolean existsById(Long lessonId) {
        return lessonJpaRepository.existsById(lessonId);
    }

    @Override
    public Optional<Lesson> findById(Long lessonId) {
        return lessonJpaRepository.findById(lessonId);
    }

    @Override
    public Optional<Lesson> findLessonById(Long lessonId) {
        return lessonJpaRepository.findLessonById(lessonId);
    }

    @Override
    public Lesson getLessonById(Long lessonId) {
        return lessonJpaRepository.findLessonById(lessonId)
                .orElseThrow(() ->
                new EntityNotFoundException(ErrorCode.NOT_FOUND_ENTITY.getMessage()));
    }

    @Override
    public List<Lesson> findByIds(List<Long> lessonIds) {
        return lessonJpaRepository.findByIds(lessonIds);
    }
}
