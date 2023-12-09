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
            AcademiesByLocationWithScroll academiesByLocationWithScroll) {
        return new AcademiesByLocationWithScrollResults(
                academiesByLocationWithScroll
                        .academiesByLocation()
                        .keySet()
                                .stream()
                                        .map(academyByLocation->
                                                    AcademiesByLocationResultWithScroll.from(
                                                            academyByLocation,
                                                            academiesByLocationWithScroll.academiesByLocation().
                                                                    get(academyByLocation)))
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

        public static AcademiesByLocationResultWithScroll from(AcademiesByLocationWithScroll.AcademyByLocation academyByLocationWithScroll,
                                                               List<Long> categories) {
            return new AcademiesByLocationResultWithScroll(
                    academyByLocationWithScroll.academyId(),
                    academyByLocationWithScroll.academyName(),
                    academyByLocationWithScroll.fullAddress(),
                    academyByLocationWithScroll.phoneNumber(),
                    categories.stream()
                            .map(CategoryInfo::getCategoryNameById)
                            .toList(),
                    academyByLocationWithScroll.latitude(),
                    academyByLocationWithScroll.longitude(),
                    academyByLocationWithScroll.shuttleAvailable(),
                    academyByLocationWithScroll.isLiked()
            );
        }

    }
}
