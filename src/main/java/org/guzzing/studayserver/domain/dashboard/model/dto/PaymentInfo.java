package org.guzzing.studayserver.domain.dashboard.model.dto;

import jakarta.validation.constraints.Positive;
import java.time.LocalDate;

public record PaymentInfo(
        @Positive Long educationFee,
        @Positive Long bookFee,
        @Positive Long shuttleFee,
        @Positive Long etcFee,
        LocalDate paymentDay
) {

}
