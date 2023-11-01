package org.guzzing.studay_server.region.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Getter
@Entity
@Table(name = "regions")
public class Region {

    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "sido", nullable = false)
    private String sido;

    @Column(name = "sigungu", nullable = false)
    private String sigungu;

    @Column(name = "upmyeondong", nullable = false)
    private String upmyeondong;

    @Column(name = "latitutde", nullable = false)
    private double latitute;

    @Column(name = "longitutde", nullable = false)
    private double longitute;

    protected Region() {
    }

    protected Region(
            final String sido,
            final String sigungu,
            final String upmyeondong,
            final double latitute,
            final double longitute
    ) {
        this.sido = sido;
        this.sigungu = sigungu;
        this.upmyeondong = upmyeondong;
        this.latitute = latitute;
        this.longitute = longitute;
    }
}
