package org.guzzing.studayserver.domain.academy.service;

import org.guzzing.studayserver.domain.academy.service.dto.result.AcademyFeeInfo;
import org.guzzing.studayserver.domain.dashboard.service.vo.AcademyInfo;
import org.guzzing.studayserver.domain.dashboard.service.vo.LessonInfo;

public interface AcademyAccessService {

    AcademyFeeInfo findAcademyFeeInfo(final Long academyId);

    void validateAcademy(final Long academyId);

    void validateLesson(final Long academyId, final Long lessonId);

    AcademyInfo findAcademyInfo(final Long academyId);

    LessonInfo findLessonInfo(final Long lessonId);
}
