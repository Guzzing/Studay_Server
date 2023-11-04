package org.guzzing.studayserver.testutil.fixture.academy;

import org.guzzing.studayserver.domain.academy.model.vo.Location;
import org.guzzing.studayserver.domain.academy.util.GeometryUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomLocationGenerator {

    private static final int RANDOM_LOCATION_SIZE = 5;
    private static final double DISTANCE = 1.0;

    public static  List<Location> make2kmRandomLocation(double latitude, double longitude) {
        Location centerLocation = Location.of(latitude, longitude);

        return generateRandomLocations(centerLocation, RANDOM_LOCATION_SIZE);

    }

    /**
     * @param centerLocation
     * @param count
     * @return 랜덤으로 DISTANCE(km) 범위 이내 있는 위도,경도를 생성해준다.
     */
    public static List<Location> generateRandomLocations(Location centerLocation, int count) {
        List<Location> randomLocations = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < count; i++) {
            double randomBearing = random.nextDouble() * 360;

            Location randomLocation = GeometryUtil.calculateLocationWithinRadiusInDirection(
                    centerLocation.getLatitude(),
                    centerLocation.getLongitude(),
                    randomBearing,
                    DISTANCE
            );
            randomLocations.add(randomLocation);
        }

        return randomLocations;
    }
}
