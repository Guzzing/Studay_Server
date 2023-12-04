package org.guzzing.studayserver.global.time;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

public class TimeConverter {

    protected TimeConverter() {
    }

    public static LocalTime getLocalTime(final String time) {
        final List<Integer> timeData = Arrays.stream(time.split(":"))
                .map(Integer::parseInt)
                .toList();

        return LocalTime.of(timeData.get(0), timeData.get(1));
    }

}
