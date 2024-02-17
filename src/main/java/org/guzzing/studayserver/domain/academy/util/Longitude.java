package org.guzzing.studayserver.domain.academy.util;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import java.util.Objects;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Longitude {
    private double value;

    public static final double KOREA_NORTHERNMOST_POINT = 132;
    public static final double KOREA_SOUTHERNMOST_POINT = 124;

    private Longitude(double value) {
        Assert.isTrue(value <= KOREA_NORTHERNMOST_POINT && value >= KOREA_SOUTHERNMOST_POINT,
            String.format( "경도(longitude)는 %s보다 크고, %s보다 작아야 합니다", KOREA_SOUTHERNMOST_POINT, KOREA_NORTHERNMOST_POINT));
        this.value = value;
    }

    public static Longitude of(double value) {
        return new Longitude(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Longitude longitude = (Longitude) o;
        return Double.compare(longitude.value, value) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

}
