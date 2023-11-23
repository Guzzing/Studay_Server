package org.guzzing.studayserver.domain.dashboard.facade.vo;

import java.time.LocalDate;
import org.guzzing.studayserver.domain.academy.model.Academy;

public record AcademyInfo(
        long academyId,
        String academyName,
        String contact,
        String fullAddress,
        String shuttleAvailability,
        long expectedFee,
        LocalDate updatedDate,
        String areaOfExpertise
) {

    public static AcademyInfo from(final Academy entity) {
        return new AcademyInfo(
                entity.getId(),
                entity.getAcademyName(),
                entity.getContact(),
                entity.getFullAddress(),
                entity.getShuttleAvailability(),
                entity.getMaxEducationFee(),
                entity.getUpdatedDate(),
                entity.getAreaOfExpertise());
    }

}
