package org.guzzing.studayserver.domain.academy.service.dto.param;

import java.util.List;
import org.guzzing.studayserver.domain.academy.repository.AcademyFilterCondition;
import org.guzzing.studayserver.domain.academy.util.SqlFormatter;

public record AcademyFilterParam(
        Double baseLatitude,
        Double baseLongitude,
        List<String> areaOfExpertises,
        Long desiredMinAmount,
        Long desiredMaxAmount
) {

    public static AcademyFilterCondition to(AcademyFilterParam param,
            String pointFormat) {
        return new AcademyFilterCondition(
                pointFormat,
                SqlFormatter.makeWhereInString(param.areaOfExpertises),
                param.desiredMinAmount,
                param.desiredMaxAmount
        );
    }

}
