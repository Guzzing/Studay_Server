package org.guzzing.studay_server.region.model.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Address {

    @Column(name = "sido", nullable = false)
    private String sido;

    @Column(name = "sigungu", nullable = false)
    private String sigungu;

    @Column(name = "upmyeondong", nullable = false)
    private String upmyeondong;

    protected Address() {
    }

    protected Address(String sido, String sigungu, String upmyeondong) {
        this.sido = sido;
        this.sigungu = sigungu;
        this.upmyeondong = upmyeondong;
    }

}
