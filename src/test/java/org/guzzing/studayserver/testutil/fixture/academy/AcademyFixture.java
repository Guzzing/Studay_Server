package org.guzzing.studayserver.testutil.fixture;

import org.guzzing.studayserver.domain.academy.model.Academy;
import org.guzzing.studayserver.domain.academy.model.Lesson;
import org.guzzing.studayserver.domain.academy.model.ReviewCount;
import org.guzzing.studayserver.domain.academy.model.vo.Address;
import org.guzzing.studayserver.domain.academy.model.vo.Location;
import org.guzzing.studayserver.domain.academy.model.vo.academyinfo.AcademyInfo;
import org.guzzing.studayserver.domain.academy.model.vo.academyinfo.ShuttleAvailability;
import org.guzzing.studayserver.domain.academy.service.dto.param.AcademiesByLocationParam;

import java.util.List;

public class AcademyFixture {

    public static List<AcademyInfo> academyInfos(){
        return List.of(
                AcademyInfo.of("유원우 코딩학원","000-0000-0000", ShuttleAvailability.AVAILABLE.name(),"예능(대)"),
                AcademyInfo.of("박세영 코딩학원","000-0000-0000", ShuttleAvailability.AVAILABLE.name(),"예능(대)"),
                AcademyInfo.of("김별 코딩학원","000-0000-0000", ShuttleAvailability.AVAILABLE.name(),"예능(대)"),
                AcademyInfo.of("김희석보스 코딩학원","000-0000-0000", ShuttleAvailability.AVAILABLE.name(),"예능(대)")
        );
    }

    public static Academy academySuwon() {
        return Academy.of(AcademyFixture.academyInfos().get(0), Address.of("경기도 분당구 수원시 망포동"), Location.of(35,127) );
    }

    public static Academy academySungnam() {
        return Academy.of(AcademyFixture.academyInfos().get(1), Address.of("경기도 분당구 성남시 망포동"), Location.of(35,127) );
    }

    public static Lesson lessonForSuwon(Academy academy) {
        return Lesson.of(academy,"자바와 객체지향","자바와 객체지향으로 떠나자","20","1개월","100000");
    }

    public static Lesson lessonForSunganm(Academy academy) {
        return Lesson.of(academy,"DB에 대해서","인덱스란 뭘까","20","1개월","100000");
    }

    public static ReviewCount reviewCountDefault(Academy academy) {
        return ReviewCount.makeDefaultReviewCount(academy);
    }

    public static AcademiesByLocationParam academiesByLocationParam() {
        return AcademiesByLocationParam.of(37.4449168,127.1388684,0);
    }




}

