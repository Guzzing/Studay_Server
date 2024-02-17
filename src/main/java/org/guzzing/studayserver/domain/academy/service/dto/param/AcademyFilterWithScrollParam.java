package org.guzzing.studayserver.domain.academy.service.dto.param;

import java.util.List;
import org.guzzing.studayserver.domain.academy.repository.dto.AcademyFilterCondition;
import org.guzzing.studayserver.domain.academy.util.CategoryInfo;
import org.guzzing.studayserver.domain.academy.util.Latitude;
import org.guzzing.studayserver.domain.academy.util.Longitude;
import org.guzzing.studayserver.domain.academy.util.SqlFormatter;

public record AcademyFilterWithScrollParam(
        Latitude baseLatitude,
        Longitude baseLongitude,
        List<String> categories,
        Long desiredMinAmount,
        Long desiredMaxAmount,
        int pageNumber
) {

    public static AcademyFilterCondition to(AcademyFilterWithScrollParam param,
            String pointFormat) {
        return new AcademyFilterCondition(
                pointFormat,
                SqlFormatter.makeWhereInString(
                        param.categories.stream()
                                .map(CategoryInfo::getCategoryIdByName)
                                .toList()),
                param.desiredMinAmount,
                param.desiredMaxAmount
        );
    }
}
