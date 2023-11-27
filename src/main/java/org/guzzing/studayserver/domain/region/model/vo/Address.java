package org.guzzing.studayserver.domain.region.model.vo;

import static lombok.AccessLevel.PROTECTED;
import static org.guzzing.studayserver.domain.region.model.vo.RegionUnit.SIDO;
import static org.guzzing.studayserver.domain.region.model.vo.RegionUnit.SIGUNGU;
import static org.guzzing.studayserver.domain.region.model.vo.RegionUnit.UPMYEONDONG;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.text.MessageFormat;
import java.util.Objects;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Embeddable
public class Address {

    @Column(name = "sido", nullable = false)
    private String sido;

    @Column(name = "sigungu", nullable = false)
    private String sigungu;

    @Column(name = "upmyeondong", nullable = false)
    private String upmyeondong;

    public Address(String sido, String sigungu, String upmyeondong) {
        validate(sido, SIDO);
        validate(sigungu, SIGUNGU);
        validate(upmyeondong, UPMYEONDONG);

        this.sido = sido;
        this.sigungu = sigungu;
        this.upmyeondong = upmyeondong;
    }

    public static Address of(String fullAddress) {
        String[] split = fullAddress.split(" ");

        validateSplitLength(split);

        if (split[2].contains("구")) {
            return new Address(split[0], split[1] + " " + split[2], split[3]);
        }

        return new Address(split[0], split[1], split[2]);
    }

    public String getFullAddress() {
        return MessageFormat.format("{0} {1} {2}", sido, sigungu, upmyeondong);
    }

    private static void validateSplitLength(String[] split) {
        if (split.length < 3) {
            throw new IllegalArgumentException("유효하지 않은 주소입니다.");
        }
    }

    private void validate(String value, RegionUnit regionUnit) {
        if (value == null) {
            String message = MessageFormat.format("{0}에 대한 지역 데이터가 주어지지 않았습니다.", regionUnit);
            throw new IllegalArgumentException(message);
        }

        if (!regionUnit.isMatched(value)) {
            String message = MessageFormat.format("{0}에 매칭되지 않는 지역 데이터입니다.", regionUnit);
            throw new IllegalArgumentException(message);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Address address = (Address) o;
        return Objects.equals(sido, address.sido) && Objects.equals(sigungu, address.sigungu)
                && Objects.equals(upmyeondong, address.upmyeondong);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sido, sigungu, upmyeondong);
    }
}
