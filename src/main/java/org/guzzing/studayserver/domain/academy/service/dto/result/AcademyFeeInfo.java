package org.guzzing.studayserver.domain.academy.service.dto.result;

import org.guzzing.studayserver.domain.academy.repository.AcademyFee;

public record AcademyFeeInfo(
        String academyName,
        long expectedFee
) {

    public static AcademyFeeInfo from(AcademyFee academyFee) {
        return new AcademyFeeInfo(
                academyFee.getAcademyName(),
                academyFee.getMaxEducationFee());
    }

}
