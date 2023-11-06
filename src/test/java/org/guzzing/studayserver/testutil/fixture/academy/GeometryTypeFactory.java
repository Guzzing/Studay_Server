package org.guzzing.studayserver.testutil.fixture.academy;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;

public final class GeometryTypeFactory {

    private static final GeometryFactory factory = new GeometryFactory();

    private GeometryTypeFactory() {
        throw new RuntimeException("유틸 클레스임!!");
    }

    public static Point createPoint(double latitude, double longitude){
        return factory.createPoint(new Coordinate(longitude, latitude));
    }

}
