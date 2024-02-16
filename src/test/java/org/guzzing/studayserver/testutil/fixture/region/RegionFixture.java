package org.guzzing.studayserver.testutil.fixture.region;

import org.guzzing.studayserver.domain.region.model.Region;
import org.guzzing.studayserver.domain.region.model.vo.Address;
import org.guzzing.studayserver.domain.region.service.dto.location.RegionGetNameParam;
import org.locationtech.jts.geom.*;

public class RegionFixture {

    public static final String SIDO = "서울특별시";
    public static final String SIGUNGU = "테스트구";
    public static final String UPMYEONDONG = "테스트테스트동";

    private static final double LATITUDE = 37.589187;
    private static final double LONGITUDE = 126.969292;

    public static Region makeRegionEntity() {
        GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);

        final Long code = 1L;

        final Address address = new Address(SIDO, SIGUNGU, UPMYEONDONG);

        final Point point = makePoint(geometryFactory, LATITUDE, LONGITUDE);

        final Polygon polygon = geometryFactory.createPolygon(new Coordinate[]{
            new Coordinate(LONGITUDE + 3, LATITUDE - 3),
            new Coordinate(LONGITUDE + 3, LATITUDE + 3),
            new Coordinate(LONGITUDE - 3, LATITUDE + 3),
            new Coordinate(LONGITUDE - 3, LATITUDE - 3),
            new Coordinate(LONGITUDE + 3, LATITUDE - 3)
        });

        final MultiPolygon area = geometryFactory.createMultiPolygon(new Polygon[]{polygon});

        return new Region(code, address, point, area);
    }

    private static Point makePoint(GeometryFactory geometryFactory, double latitude, double longitude) {
        final Coordinate coordinate = new Coordinate(latitude, longitude);
        return geometryFactory.createPoint(coordinate);
    }

    public static RegionGetNameParam regionGetNameParam() {
        return new RegionGetNameParam(
            LATITUDE, LONGITUDE
        );
    }

}
