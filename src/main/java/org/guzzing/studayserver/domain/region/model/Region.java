package org.guzzing.studayserver.domain.region.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Getter
@Entity
@Table(name = "regions")
public class Region {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sido", nullable = false)
    private String sido;

    @Column(name = "sigungu", nullable = false)
    private String sigungu;

    @Column(name = "upmyeondong", nullable = false)
    private String upmyeondong;

    @Column(name = "latitude", nullable = false)
    private double latitude;

    @Column(name = "longitude", nullable = false)
    private double longitude;

    protected Region() {
    }

    protected Region(
            final String sido,
            final String sigungu,
            final String upmyeondong,
            final double latitude,
            final double longitude
    ) {
        this.sido = sido;
        this.sigungu = sigungu;
        this.upmyeondong = upmyeondong;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public static Region of(
            final String sido, final String sigungu, final String upmyeondong,
            final double latitude, final double longitude
    ) {
        return new Region(sido, sigungu, upmyeondong, latitude, longitude);
    }
}
