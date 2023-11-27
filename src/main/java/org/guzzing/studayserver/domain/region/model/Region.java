package org.guzzing.studayserver.domain.region.model;

import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.guzzing.studayserver.domain.region.model.vo.Address;
import org.locationtech.jts.geom.MultiPolygon;
import org.locationtech.jts.geom.Point;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
@Table(name = "regions")
public class Region {

    @Transient
    public static final List<String> BASE_REGION_SIDO = List.of("서울특별시", "경기도");
    @Transient
    public static final List<String> SIGUNGU_POSTFIX = List.of("시", "구", "군");
    @Transient
    public static final List<String> UPMYEONDONG_POSTFIX = List.of("읍", "면", "동", "군", "구");

    @Id
    @Column(name = "code", nullable = false)
    private Long id;

    @Embedded
    private Address address;

    @Column(name = "point", nullable = false)
    private Point point;

    @Column(name = "area", nullable = true)
    private MultiPolygon area;

    public Region(Long id, Address address, Point point, MultiPolygon area) {
        this.id = id;
        this.address = address;
        this.point = point;
        this.area = area;
    }

    public String getSido() {
        return this.address.getSido();
    }

    public String getSigungu() {
        return this.address.getSigungu();
    }

    public String getUpmyeondong() {
        return this.address.getUpmyeondong();
    }

}
