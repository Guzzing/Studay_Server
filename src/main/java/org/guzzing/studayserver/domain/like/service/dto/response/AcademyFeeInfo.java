package org.guzzing.studayserver.domain.like.service.dto.response;

import org.guzzing.studayserver.domain.academy.repository.AcademyFee;

public record AcademyFeeInfo(
        String academyName,
        Long expectedFee
) {

    public static AcademyFeeInfo to(AcademyFee academyFee) {
        return new AcademyFeeInfo(
                academyFee.getAcademyName(),
                academyFee.getMaxEducationFee()
        );
    }

}
