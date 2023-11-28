package org.guzzing.studayserver.domain.academy.service.dto.result;

import org.guzzing.studayserver.domain.academy.util.CategoryInfo;
import org.guzzing.studayserver.domain.academy.util.dto.DistinctFilteredAcademy;

import java.util.List;

public record AcademyFilterResult(
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

    public static AcademyFilterResult from(DistinctFilteredAcademy distinctFilteredAcademy, List<Long> categoryIds) {
        return new AcademyFilterResult(
                distinctFilteredAcademy.academyId(),
                distinctFilteredAcademy.academyName(),
                distinctFilteredAcademy.fullAddress(),
                distinctFilteredAcademy.phoneNumber(),
                categoryIds.stream()
                        .map(categoryId -> CategoryInfo.getCategoryNameById(categoryId))
                        .toList(),
                distinctFilteredAcademy.latitude(),
                distinctFilteredAcademy.longitude(),
                distinctFilteredAcademy.shuttleAvailable(),
                distinctFilteredAcademy.isLiked()
        );
    }

}
