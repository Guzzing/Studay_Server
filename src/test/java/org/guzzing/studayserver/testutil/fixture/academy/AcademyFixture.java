package org.guzzing.studayserver.testutil.fixture.academy;

import java.util.ArrayList;
import java.util.List;
import org.guzzing.studayserver.domain.academy.model.Academy;
import org.guzzing.studayserver.domain.academy.model.Lesson;
import org.guzzing.studayserver.domain.academy.model.ReviewCount;
import org.guzzing.studayserver.domain.academy.model.vo.Address;
import org.guzzing.studayserver.domain.academy.model.vo.Location;
import org.guzzing.studayserver.domain.academy.model.vo.academyinfo.AcademyInfo;
import org.guzzing.studayserver.domain.academy.model.vo.academyinfo.ShuttleAvailability;
import org.guzzing.studayserver.domain.academy.service.dto.param.AcademiesByLocationParam;
import org.guzzing.studayserver.domain.academy.service.dto.param.AcademyFilterParam;
import org.locationtech.jts.geom.Point;

public class AcademyFixture {

    private static final double LATITUDE = 37.4449168;
    private static final double LONGITUDE = 127.1388684;

    public static List<AcademyInfo> academyInfos() {
        return List.of(
                AcademyInfo.of("유원우 코딩학원", "000-0000-0000", ShuttleAvailability.AVAILABLE.name(), "예능(대)"),
                AcademyInfo.of("박세영 코딩학원", "000-0000-0000", ShuttleAvailability.AVAILABLE.name(), "예능(대)"),
                AcademyInfo.of("김별 코딩학원", "000-0000-0000", ShuttleAvailability.AVAILABLE.name(), "예능(대)"),
                AcademyInfo.of("김희석보스 코딩학원", "000-0000-0000", ShuttleAvailability.AVAILABLE.name(), "예능(대)"),
                AcademyInfo.of("김유진 코딩학원", "000-0000-0000", ShuttleAvailability.AVAILABLE.name(), "예능(대)"),
                AcademyInfo.of("김지성 코딩학원", "000-0000-0000", ShuttleAvailability.AVAILABLE.name(), "예능(대)")
        );
    }

    public static Academy academySungnam() {
        Academy academy = Academy.of(AcademyFixture.academyInfos().get(1), Address.of("경기도 성남시 중원구 망포동"),
                Location.of(LATITUDE, LONGITUDE));
        academy.changePoint(GeometryTypeFactory.createPoint(LATITUDE, LONGITUDE));
        return academy;
    }

    public static List<Academy> academies() {
        return academyInfos().stream()
                .map(academyInfo -> {
                    Academy academy = Academy.of(academyInfo, Address.of("경기도 성남시 중원구 망포동"),
                            Location.of(37.4449168, 127.1388684));
                    academy.changePoint(GeometryTypeFactory.createPoint(LATITUDE, LONGITUDE));
                    return academy;
                }).toList();
    }

    /**
     * @param latitude
     * @param longitude
     * @return 반경 1km 이내에 있는 학원들
     */
    public static List<Academy> randomAcademiesWithinDistance(double latitude, double longitude) {
        List<Location> randomLocations = RandomLocationGenerator.make2kmRandomLocation(latitude, longitude);
        List<Academy> academies = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            Academy academy = Academy.of(academyInfos().get(i), Address.of("경기도 성남시 중원구 망포동"), randomLocations.get(i));
            academies.add(academy);
            Point point = GeometryTypeFactory.createPoint(
                    randomLocations.get(i).getLatitude(),
                    randomLocations.get(i).getLongitude()
            );
            academy.changePoint(point);
        }

        return academies;
    }

    public static Lesson lessonForSunganm(Academy academy) {
        return Lesson.of(academy, "DB에 대해서", "인덱스란 뭘까", "20", "1개월", "100000");
    }

    public static ReviewCount reviewCountDefault(Academy academy) {
        return ReviewCount.makeDefaultReviewCount(academy);
    }

    public static AcademiesByLocationParam academiesByLocationParam(double latitude, double longitude) {
        return AcademiesByLocationParam.of(latitude, longitude);
    }

    public static AcademyFilterParam academyFilterParam(Double latitude, Double longitude, Long desiredMinAmount,
            Long desiredMaxAmount) {
        return new AcademyFilterParam(latitude, longitude, List.of("예능(대)"), desiredMinAmount, desiredMaxAmount);
    }

}
