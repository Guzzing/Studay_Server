package org.guzzing.studayserver.domain.dashboard.model.vo;

import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Embeddable
public class FeeInfo {

    @Column(name = "education_fee", nullable = true)
    private Long educationFee;

    @Column(name = "book_fee", nullable = true)
    private Long bookFee;

    @Column(name = "shuttle_fee", nullable = true)
    private Long shuttleFee;

    @Column(name = "etc_fee", nullable = true)
    private Long etcFee;

    @Column(name = "payment_day", nullable = true)
    private LocalDate paymentDay;

    public FeeInfo(
            final Long educationFee,
            final Long bookFee,
            final Long shuttleFee,
            final Long etcFee,
            final LocalDate paymentDay
    ) {
        this.educationFee = educationFee;
        this.bookFee = bookFee;
        this.shuttleFee = shuttleFee;
        this.etcFee = etcFee;
        this.paymentDay = paymentDay;
    }

}
