package org.guzzing.studayserver.domain.academy.service;

import org.guzzing.studayserver.domain.like.service.dto.response.AcademyFeeInfo;

public interface AcademyAccessService {

    AcademyFeeInfo findAcademyFeeInfo(final Long academyId);

    void validateAcademy(final Long academyId);

    void validateLesson(final Long lessonId);

}
