package org.guzzing.studayserver.testutil.fixture.academy;

import org.guzzing.studayserver.global.error.response.ErrorCode;
import org.guzzing.studayserver.global.error.response.ErrorResponse;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;

public final class GeometryTypeFactory {

    private static final GeometryFactory factory = new GeometryFactory(new PrecisionModel(), 4326);

    private GeometryTypeFactory() {
        throw new RuntimeException(ErrorCode.UTIL_NOT_CONSTRUCTOR.getMessage());
    }

    public static Point createPoint(double latitude, double longitude) {
        return factory.createPoint(new Coordinate(longitude, latitude));
    }

}
