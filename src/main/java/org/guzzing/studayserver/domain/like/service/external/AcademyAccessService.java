package org.guzzing.studayserver.domain.like.service.external;

import org.guzzing.studayserver.domain.like.service.dto.response.AcademyFeeInfo;

public interface AcademyAccessService {

    AcademyFeeInfo findAcademyFeeInfo(final Long academyId);

}
