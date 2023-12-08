package org.guzzing.studayserver.domain.academy.service.dto.param;

import java.util.List;
import org.guzzing.studayserver.domain.academy.repository.dto.AcademyFilterCondition;
import org.guzzing.studayserver.domain.academy.util.CategoryInfo;
import org.guzzing.studayserver.domain.academy.util.SqlFormatter;

public record AcademyFilterWithScrollParam(
        Double baseLatitude,
        Double baseLongitude,
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
                                .map(categoryName -> CategoryInfo.getCategoryIdByName(categoryName))
                                .toList()),
                param.desiredMinAmount,
                param.desiredMaxAmount
        );
    }
}
