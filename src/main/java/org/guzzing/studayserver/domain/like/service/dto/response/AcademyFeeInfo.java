package org.guzzing.studayserver.domain.like.service.dto.response;

import org.guzzing.studayserver.domain.academy.repository.AcademyFee;

public record AcademyFeeInfo(
        Long academyId,
        String academyName,
        Long expectedFee
) {

    public static AcademyFeeInfo to(AcademyFee academyFee) {
        return new AcademyFeeInfo(
                academyFee.getId(),
                academyFee.getAcademyName(),
                academyFee.getMaxEducationFee());
    }

}
