package org.guzzing.studayserver.domain.academy.util;

import org.guzzing.studayserver.domain.academy.model.vo.Location;
import org.guzzing.studayserver.global.error.response.ErrorCode;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;

public class GeometryUtil {

    private GeometryUtil() {
        throw new RuntimeException(ErrorCode.UTIL_NOT_CONSTRUCTOR.getMessage());
    }

    private static final GeometryFactory factory = new GeometryFactory(new PrecisionModel(), 4326);
    private static final Double EARTH_RADIUS = 6371.01;

    public static Location calculateLocationWithinRadiusInDirection(
            Double baseLatitude,
            Double baseLongitude,
            Double bearing,
            Double distance) {
        Double radianLatitude = toRadian(baseLatitude);
        Double radianLongitude = toRadian(baseLongitude);
        Double radianAngle = toRadian(bearing);

        Double distanceRadius = distance / EARTH_RADIUS;

        Double latitude = Math.asin(sin(radianLatitude) * cos(distanceRadius) +
                cos(radianLatitude) * sin(distanceRadius) * cos(radianAngle));
        Double longitude = radianLongitude + Math.atan2(sin(radianAngle) * sin(distanceRadius) *
                cos(radianLatitude), cos(distanceRadius) - sin(radianLatitude) * sin(latitude));
        longitude = normalizeLongitude(longitude);

        return Location.of(toDegree(latitude), toDegree(longitude));
    }

    public static String makeDiagonal(
            Latitude baseLatitude,
            Longitude baseLongitude,
            Double distance) {
        Location northEast = calculateLocationWithinRadiusInDirection(
                baseLatitude.getValue(),
                baseLongitude.getValue(),
                Direction.NORTHEAST.getBearing(),
                distance);
        Location southWest = calculateLocationWithinRadiusInDirection(
                baseLatitude.getValue(),
                baseLongitude.getValue(),
                Direction.SOUTHWEST.getBearing(),
                distance);

        return SqlFormatter.makeDiagonalByLineString(northEast, southWest);
    }

    public static Point createPoint(double latitude, double longitude) {
        return factory.createPoint(new Coordinate(longitude, latitude));
    }

    private static Double toRadian(Double coordinate) {
        return coordinate * Math.PI / 180.0;
    }

    private static Double toDegree(Double coordinate) {
        return coordinate * 180.0 / Math.PI;
    }

    private static Double sin(Double coordinate) {
        return Math.sin(coordinate);
    }

    private static Double cos(Double coordinate) {
        return Math.cos(coordinate);
    }

    private static Double normalizeLongitude(Double longitude) {
        return (longitude + 540) % 360 - 180;
    }

}
