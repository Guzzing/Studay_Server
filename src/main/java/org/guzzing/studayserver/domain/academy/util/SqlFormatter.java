package org.guzzing.studayserver.domain.academy.util;

import java.util.List;
import java.util.stream.Collectors;

import org.guzzing.studayserver.domain.academy.model.vo.Location;
import org.guzzing.studayserver.global.error.response.ErrorCode;

public class SqlFormatter {

    private static final String LINESTRING_SQL = "'LINESTRING(%f %f, %f %f)',4326)";

    private SqlFormatter() {
        throw new RuntimeException(ErrorCode.UTIL_NOT_CONSTRUCTOR.getMessage());
    }

    public static String makeWhereInString(List<Long> values) {
        return "(" + values.stream().map(Object::toString).collect(Collectors.joining(", ")) + ")";
    }

    public static String makeDiagonalByLineString(Location northEast, Location southWest) {
        return String.format(
                LINESTRING_SQL,
                northEast.getLatitude(), northEast.getLongitude(), southWest.getLatitude(), southWest.getLongitude()
        );

    }
}
