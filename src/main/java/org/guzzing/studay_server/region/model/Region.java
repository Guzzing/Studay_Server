package org.guzzing.studay_server.region.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.guzzing.studay_server.region.model.vo.Address;
import org.guzzing.studay_server.region.model.vo.Location;

@Entity
@Table(name = "regions")
public class Region {

    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Embedded
    private Address address;

    @Embedded
    private Location location;

    protected Region() {
    }

    protected Region(Address address, Location location) {
        this.address = address;
        this.location = location;
    }
}
