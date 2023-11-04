package org.guzzing.studayserver.domain.academy.model;

import jakarta.persistence.*;
import lombok.Getter;
import org.guzzing.studayserver.domain.academy.model.vo.Address;
import org.guzzing.studayserver.domain.academy.model.vo.Location;
import org.guzzing.studayserver.domain.academy.model.vo.academyinfo.AcademyInfo;
import org.guzzing.studayserver.global.BaseEntity;
import org.locationtech.jts.geom.Point;

import java.util.Objects;

@Getter
@Entity
@Table(name = "academies")
public class Academy extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private AcademyInfo academyInfo;

    @Embedded
    private Address fullAddress;

    @Embedded
    private Location location;

    private Long maxEducationFee;

    private Point point;

    protected Academy(
            final AcademyInfo academyInfo,
            final Address fullAddress,
            final Location location
    ) {
        this.academyInfo = academyInfo;
        this.fullAddress = fullAddress;
        this.location = location;
    }

    protected Academy() {
    }

    public static Academy of(final AcademyInfo academyInfo, final Address address, final Location location) {
        return new Academy(academyInfo, address, location);
    }

    public void changeEducationFee(Long maxEducationFee) {
        this.maxEducationFee = maxEducationFee;
    }

    public String getFullAddress() {
        return fullAddress.getFullAddress();
    }

    public String getName() {
        return academyInfo.getName();
    }

    public String getContact() {
        return academyInfo.getContact();
    }

    public String getShuttleAvailability() {
        return academyInfo.getShuttle().toString();
    }

    public String getAreaOfExpertise() {
        return academyInfo.getAreaOfExpertise();
    }

    public void changePoint(Point point) {
        this.point = point;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Academy academy = (Academy) o;
        return Objects.equals(id, academy.id) && Objects.equals(academyInfo, academy.academyInfo) && Objects.equals(fullAddress, academy.fullAddress) && Objects.equals(location, academy.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, academyInfo, fullAddress, location);
    }

}
