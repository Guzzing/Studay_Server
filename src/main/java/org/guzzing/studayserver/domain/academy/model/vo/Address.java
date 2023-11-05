package org.guzzing.studayserver.domain.academy.model.vo;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.Objects;
import lombok.Getter;
import org.springframework.util.Assert;

@Getter
@Embeddable
public class Address {

    @Column(name = "full_address", nullable = false)
    private String fullAddress;

    protected Address(final String address) {
        Assert.isTrue(StringUtils.isNotBlank(address), "주소 정보는 반드시 주어져야 합니다.");
        this.fullAddress = address;
    }

    protected Address() {
    }

    public static Address of(final String address) {
        return new Address(address);
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
        return Objects.equals(fullAddress, address.fullAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fullAddress);
    }

}
