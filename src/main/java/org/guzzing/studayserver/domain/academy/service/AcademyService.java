package org.guzzing.studayserver.domain.academy.service;

import java.util.List;
import org.guzzing.studayserver.domain.academy.model.Academy;
import org.guzzing.studayserver.domain.academy.model.Lesson;
import org.guzzing.studayserver.domain.academy.model.ReviewCount;
import org.guzzing.studayserver.domain.academy.repository.academy.AcademyRepository;
import org.guzzing.studayserver.domain.academy.repository.lesson.LessonRepository;
import org.guzzing.studayserver.domain.academy.repository.review.ReviewCountRepository;
import org.guzzing.studayserver.domain.academy.service.dto.AcademyGetResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AcademyService {

    private final AcademyRepository academyRepository;
    private final LessonRepository lessonRepository;
    private final ReviewCountRepository reviewCountRepository;

    public AcademyService(AcademyRepository academyRepository, LessonRepository lessonRepository,
            ReviewCountRepository reviewCountRepository) {
        this.academyRepository = academyRepository;
        this.lessonRepository = lessonRepository;
        this.reviewCountRepository = reviewCountRepository;
    }

    @Transactional(readOnly = true)
    public AcademyGetResult getAcademy(Long academyId) {
        Academy academy = academyRepository.getById(academyId);
        List<Lesson> lessons = lessonRepository.findAllByAcademyId(academyId);
        ReviewCount reviewCount = reviewCountRepository.getByAcademyId(academyId);

        return AcademyGetResult.from(academy, lessons, reviewCount);
    }

}
