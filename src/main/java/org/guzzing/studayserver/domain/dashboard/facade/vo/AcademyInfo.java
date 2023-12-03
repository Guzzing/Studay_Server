package org.guzzing.studayserver.domain.dashboard.facade.vo;

import java.time.LocalDate;
import java.util.List;
import org.guzzing.studayserver.domain.academy.model.Academy;
import org.guzzing.studayserver.domain.academy.util.CategoryInfo;

public record AcademyInfo(
        long academyId,
        String academyName,
        String contact,
        String fullAddress,
        String shuttleAvailability,
        long expectedFee,
        LocalDate updatedDate,
        List<String> categories
) {

    public static AcademyInfo from(final Academy entity, final List<Long> categoryIds) {
        final List<String> categories = categoryIds.stream()
                .map(CategoryInfo::getCategoryNameById)
                .toList();

        return new AcademyInfo(
                entity.getId(),
                entity.getAcademyName(),
                entity.getContact(),
                entity.getFullAddress(),
                entity.getShuttleAvailability(),
                entity.getMaxEducationFee(),
                entity.getUpdatedDate(),
                categories);
    }

}
