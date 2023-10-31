package org.guzzing.studay_server.region.model.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Location {

    @Column(name = "latitutde", nullable = false)
    private double latitute;

    @Column(name = "longitutde", nullable = false)
    private double longitute;

    protected Location() {
    }

    protected Location(double latitute, double longitute) {
        this.latitute = latitute;
        this.longitute = longitute;
    }

}
