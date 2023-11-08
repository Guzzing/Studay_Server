package org.guzzing.studayserver.domain.dashboard.model.dto;

public record PaymentInfo(
        Long educationFee,
        Long bookFee,
        Long shuttleFee,
        Long etcFee
) {

}
