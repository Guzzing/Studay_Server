package org.guzzing.studayserver.domain.academy.service.dto.result;

import java.util.List;
import java.util.Map;
import org.guzzing.studayserver.domain.academy.repository.dto.AcademiesByLocationWithScroll;
import org.guzzing.studayserver.domain.academy.repository.dto.AcademyByLocationWithScroll;
import org.guzzing.studayserver.domain.academy.util.CategoryInfo;

public record AcademiesByLocationWithScrollResults(
        List<AcademiesByLocationResultWithScroll> academiesByLocationResults,
        boolean hasNext
) {

    public static AcademiesByLocationWithScrollResults to(
            AcademiesByLocationWithScroll academiesByLocationWithScroll,
            Map<Long, List<Long>> academyIdWithCategories) {
        return new AcademiesByLocationWithScrollResults(
                academiesByLocationWithScroll
                        .academiesByLocation()
                        .stream()
                        .map(
                                academyByLocationWithScroll ->
                                        AcademiesByLocationResultWithScroll.from(
                                                academyByLocationWithScroll,
                                                academyIdWithCategories.get(academyByLocationWithScroll.academyId())
                                        ))
                        .toList(),
                academiesByLocationWithScroll.hasNext());
    }

    public record AcademiesByLocationResultWithScroll(
            Long academyId,
            String academyName,
            String address,
            String contact,
            List<String> categories,
            Double latitude,
            Double longitude,
            String shuttleAvailable,
            boolean isLiked
    ) {

        public static AcademiesByLocationResultWithScroll from(AcademyByLocationWithScroll academyByLocationWithScroll,
                List<Long> categories) {
            return new AcademiesByLocationResultWithScroll(
                    academyByLocationWithScroll.academyId(),
                    academyByLocationWithScroll.academyName(),
                    academyByLocationWithScroll.fullAddress(),
                    academyByLocationWithScroll.phoneNumber(),
                    categories.stream()
                            .map(categoryId -> CategoryInfo.getCategoryNameById(categoryId))
                            .toList(),
                    academyByLocationWithScroll.latitude(),
                    academyByLocationWithScroll.longitude(),
                    academyByLocationWithScroll.shuttleAvailable(),
                    academyByLocationWithScroll.isLiked()
            );
        }

    }
}
