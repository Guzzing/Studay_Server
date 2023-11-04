package org.guzzing.studayserver.domain.academy.service;

import org.guzzing.studayserver.domain.like.service.dto.response.AcademyFeeInfo;

public interface AcademyAccessService {

    AcademyFeeInfo findAcademyFeeInfo(final Long academyId);

    boolean existsAcademy(final Long academyId);

}
