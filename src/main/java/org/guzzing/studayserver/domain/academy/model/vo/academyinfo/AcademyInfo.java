package org.guzzing.studayserver.domain.academy.model.vo.academyinfo;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.util.Objects;
import lombok.Getter;
import org.springframework.util.Assert;

@Embeddable
public class AcademyInfo {

    @Getter
    @Column(name = "academy_name", nullable = false)
    private String academyName;

    @Embedded
    private PhoneNumber contact;

    @Enumerated(value = EnumType.STRING)
    private ShuttleAvailability shuttle;

    @Column(name = "area_of_expertise", nullable = false)
    private String areaOfExpertise;

    protected AcademyInfo(final String academyName, final String contact, final String shuttle, final String areaOfExpertise) {
        Assert.isTrue(StringUtils.isNotBlank(academyName), "학원명이 주어지지 않았습니다.");

        this.academyName = academyName;
        this.contact = new PhoneNumber(contact);
        this.shuttle = ShuttleAvailability.getShuttleAvailability(shuttle);
        this.areaOfExpertise = areaOfExpertise;
    }

    public static AcademyInfo of(final String name, final String contact, final String shuttle,
            final String areaOfExpertise) {
        return new AcademyInfo(name, contact, shuttle, areaOfExpertise);
    }

    protected AcademyInfo() {
    }

    public String getContact() {
        return contact.getContact();
    }

    public String getShuttle() {
        return shuttle.name();
    }

    public String getAreaOfExpertise() {
        return areaOfExpertise;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AcademyInfo that = (AcademyInfo) o;
        return Objects.equals(academyName, that.academyName) && Objects.equals(contact, that.contact) && shuttle == that.shuttle;
    }

    @Override
    public int hashCode() {
        return Objects.hash(academyName, contact, shuttle);
    }

}
