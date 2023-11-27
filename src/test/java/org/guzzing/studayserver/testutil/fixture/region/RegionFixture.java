package org.guzzing.studayserver.testutil.fixture.region;

import org.guzzing.studayserver.domain.region.model.Region;
import org.guzzing.studayserver.domain.region.model.vo.Address;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.MultiPolygon;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;

public class RegionFixture {

    public static final String sido = "서울특별시";
    public static final String sigungu = "테스트구";
    public static final String upmyeondong = "테스트테스트동";

    public static Region makeRegionEntity() {
        GeometryFactory geometryFactory = new GeometryFactory();

        final Long code = 1L;

        final Address address = new Address(sido, sigungu, upmyeondong);

        final Point point = makePoint(geometryFactory, 37.589187, 126.969292);

        final Polygon polygon = geometryFactory.createPolygon(new Coordinate[]{
                new Coordinate(0, 0), new Coordinate(1, 0), new Coordinate(1, 1), new Coordinate(0, 1),
                new Coordinate(0, 0)
        });

        final MultiPolygon area = geometryFactory.createMultiPolygon(new Polygon[]{polygon});

        return new Region(code, address, point, area);
    }

    private static Point makePoint(GeometryFactory geometryFactory, double latitude, double longitude) {
        final Coordinate coordinate = new Coordinate(latitude, longitude);
        return geometryFactory.createPoint(coordinate);
    }

}
