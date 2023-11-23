package org.guzzing.studayserver.domain.academy.service;

import org.guzzing.studayserver.domain.academy.model.Academy;
import org.guzzing.studayserver.domain.academy.model.Lesson;
import org.guzzing.studayserver.domain.academy.repository.academy.AcademyRepository;
import org.guzzing.studayserver.domain.academy.repository.lesson.LessonRepository;
import org.guzzing.studayserver.domain.academy.service.dto.result.AcademyAndLessonDetailResult;
import org.guzzing.studayserver.domain.dashboard.service.vo.AcademyInfo;
import org.guzzing.studayserver.domain.dashboard.service.vo.LessonInfo;
import org.guzzing.studayserver.domain.like.service.dto.response.AcademyFeeInfo;
import org.guzzing.studayserver.global.exception.AcademyException;
import org.guzzing.studayserver.global.exception.LessonException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class AcademyAccessServiceImpl implements
        AcademyAccessService {

    private final AcademyRepository academyRepository;
    private final LessonRepository lessonRepository;
    private final AcademyService academyService;

    public AcademyAccessServiceImpl(
            final AcademyRepository academyRepository,
            final LessonRepository lessonRepository,
            AcademyService academyService) {
        this.academyRepository = academyRepository;
        this.lessonRepository = lessonRepository;
        this.academyService = academyService;
    }

    @Override
    public AcademyFeeInfo findAcademyFeeInfo(Long academyId) {
        return AcademyFeeInfo.to(academyRepository.findAcademyFeeInfo(academyId));
    }

    @Override
    public void validateAcademy(final Long academyId) {
        final boolean existsAcademy = academyRepository.existsById(academyId);

        if (!existsAcademy) {
            throw new AcademyException("존재하지 않는 학원입니다.");
        }
    }

    @Override
    public void validateLesson(Long lessonId) {
        final boolean existsLesson = lessonRepository.existsById(lessonId);

        if (!existsLesson) {
            throw new LessonException("존재하지 않는 수업입니다.");
        }
    }

    @Override
    public AcademyInfo findAcademyInfo(final Long academyId) {
        final Academy academy = academyRepository.findAcademyById(academyId)
                .orElseThrow(() -> new AcademyException("존재하지 않는 학원입니다."));

        return AcademyInfo.from(academy);
    }

    @Override
    public LessonInfo findLessonInfo(Long lessonId) {
        final Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new LessonException("존재하지 않는 수업입니다."));

        return LessonInfo.from(lesson);
    }

    @Override
    public AcademyAndLessonDetailResult getAcademyAndLessonDetail(Long lessonId) {
        return academyService.getAcademyAndLessonDetail(lessonId);
    }

}
