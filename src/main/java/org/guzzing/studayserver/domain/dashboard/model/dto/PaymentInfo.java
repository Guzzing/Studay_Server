package org.guzzing.studayserver.domain.dashboard.model.dto;

import java.time.LocalDate;

public record PaymentInfo(
        Long educationFee,
        Long bookFee,
        Long shuttleFee,
        Long etcFee,
        LocalDate paymentDay
) {

}
