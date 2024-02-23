package org.guzzing.studayserver.domain.academy.service.dto.result;

import java.util.List;

import org.guzzing.studayserver.domain.academy.repository.dto.AcademyWithLikeByLocation;
import org.guzzing.studayserver.domain.academy.repository.dto.response.AcademiesByLocationWithScrollRepositoryResponse;
import org.guzzing.studayserver.domain.academy.util.CategoryInfo;

public record AcademiesByLocationWithScrollResults(
        List<AcademiesByLocationResultWithScroll> academiesByLocationResults,
        boolean hasNext
) {

    public static AcademiesByLocationWithScrollResults to(
            AcademiesByLocationWithScrollRepositoryResponse academiesByLocationWithScrollRepositoryResponse) {
        return new AcademiesByLocationWithScrollResults(
                academiesByLocationWithScrollRepositoryResponse
                        .academiesByLocation()
                        .keySet()
                        .stream()
                        .map(academyByLocation ->
                                AcademiesByLocationResultWithScroll.from(
                                        academyByLocation,
                                        academiesByLocationWithScrollRepositoryResponse.academiesByLocation().
                                                get(academyByLocation)))
                        .toList(),
                academiesByLocationWithScrollRepositoryResponse.hasNext());
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

        public static AcademiesByLocationResultWithScroll from(
            AcademyWithLikeByLocation academyByLocationWithScroll,
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
