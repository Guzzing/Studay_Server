package org.guzzing.studayserver.domain.academy.util;

import java.util.List;
import org.guzzing.studayserver.domain.academy.model.vo.Location;
import org.guzzing.studayserver.global.error.response.ErrorCode;

public class SqlFormatter {

    private static final String LINESTRING_SQL = "'LINESTRING(%f %f, %f %f)',4326)";

    private SqlFormatter() {
        throw new RuntimeException(ErrorCode.UTIL_NOT_CONSTRUCTOR.getMessage());
    }

    public static String makeWhereInString(List<String> values) {
        StringBuilder builder = new StringBuilder("(");
        for (int i = 0; i < values.size(); i++) {
            builder.append("'");
            builder.append(values.get(i));
            builder.append("'");
            if (i < values.size() - 1) {
                builder.append(", ");
            }
        }
        builder.append(")");
        return builder.toString();
    }

    public static String makeDiagonalByLineString(Location northEast, Location southWest) {
        return String.format(
                LINESTRING_SQL,
                northEast.getLatitude(), northEast.getLongitude(), southWest.getLatitude(), southWest.getLongitude()
        );

    }
}
