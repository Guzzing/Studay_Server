package org.guzzing.studayserver.domain.academy.service.dto.result;

import java.util.List;
import java.util.Map;
import org.guzzing.studayserver.domain.academy.repository.dto.AcademiesByFilterWithScroll;
import org.guzzing.studayserver.domain.academy.repository.dto.AcademyByFilterWithScroll;
import org.guzzing.studayserver.domain.academy.util.CategoryInfo;

public record AcademiesFilterWithScrollResults(
        List<AcademyFilterWithScrollResult> academiesFilterWithScrollResults,
        boolean hasNext
) {

    public static AcademiesFilterWithScrollResults from(AcademiesByFilterWithScroll academiesByFilterWithScroll,
            Map<Long, List<Long>> academyIdWithCategories) {

        return new AcademiesFilterWithScrollResults(
                academiesByFilterWithScroll.academiesByLocation()
                        .stream()
                        .map(academyByFilterWithScroll ->
                                AcademyFilterWithScrollResult.from(
                                        academyByFilterWithScroll,
                                        academyIdWithCategories.get(academyByFilterWithScroll.academyId())))
                        .toList(),
                academiesByFilterWithScroll.hasNext()
        );

    }

    public record AcademyFilterWithScrollResult(
            Long academyId,
            String academyName,
            String fullAddress,
            String contact,
            List<String> categories,
            Double latitude,
            Double longitude,
            String shuttleAvailable,
            boolean isLiked
    ) {

        public static AcademyFilterWithScrollResult from(AcademyByFilterWithScroll academyByFilterWithScroll,
                List<Long> categoryIds) {
            return new AcademyFilterWithScrollResult(
                    academyByFilterWithScroll.academyId(),
                    academyByFilterWithScroll.academyName(),
                    academyByFilterWithScroll.fullAddress(),
                    academyByFilterWithScroll.phoneNumber(),
                    categoryIds.stream()
                            .map(CategoryInfo::getCategoryNameById)
                            .toList(),
                    academyByFilterWithScroll.latitude(),
                    academyByFilterWithScroll.longitude(),
                    academyByFilterWithScroll.shuttleAvailable(),
                    academyByFilterWithScroll.isLiked()
            );
        }

    }

}
