package org.guzzing.studayserver.domain.like.service.dto.response;

import org.guzzing.studayserver.domain.academy.repository.dto.AcademyFee;

public record AcademyFeeInfo(
        String academyName,
        long expectedFee
) {

    public static AcademyFeeInfo to(AcademyFee academyFee) {
        return new AcademyFeeInfo(
                academyFee.getAcademyName(),
                academyFee.getMaxEducationFee()
        );
    }

}
