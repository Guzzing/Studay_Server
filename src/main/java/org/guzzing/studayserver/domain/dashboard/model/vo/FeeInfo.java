package org.guzzing.studayserver.domain.dashboard.model.vo;

import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.StringJoiner;
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

    protected FeeInfo(
            final Long educationFee,
            final Long bookFee,
            final Long shuttleFee,
            final Long etcFee
    ) {
        this.educationFee = educationFee;
        this.bookFee = bookFee;
        this.shuttleFee = shuttleFee;
        this.etcFee = etcFee;
    }

    public static FeeInfo of(
            final Long educationFee,
            final Long bookFee,
            final Long shuttleFee,
            final Long etcFee
    ) {
        return new FeeInfo(educationFee, bookFee, shuttleFee, etcFee);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", FeeInfo.class.getSimpleName() + "[", "]")
                .add("educationFee=" + educationFee)
                .add("bookFee=" + bookFee)
                .add("shuttleFee=" + shuttleFee)
                .add("etcFee=" + etcFee)
                .toString();
    }
}
