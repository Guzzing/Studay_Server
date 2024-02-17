package org.guzzing.studayserver.domain.academy.util;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import java.util.Objects;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Latitude {
    private double value;

    public static final double KOREA_NORTHERNMOST_POINT = 43;
    public static final double KOREA_SOUTHERNMOST_POINT = 33;

    private Latitude(double value) {
        Assert.isTrue(value <= KOREA_NORTHERNMOST_POINT && value >= KOREA_SOUTHERNMOST_POINT,
            String.format( "위도(latitude)는 %s보다 크고, %s보다 작아야 합니다", KOREA_SOUTHERNMOST_POINT, KOREA_NORTHERNMOST_POINT));
        this.value = value;
    }

    public static Latitude of(double value) {
        return new Latitude(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Latitude latitude = (Latitude) o;
        return Double.compare(latitude.value, value) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
