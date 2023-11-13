package org.guzzing.studayserver.domain.dashboard.service.vo;

import org.guzzing.studayserver.domain.academy.model.Academy;

public record AcademyInfo(
        long academyId,
        String academyName,
        String address
) {

    public static AcademyInfo from(final Academy entity) {
        return new AcademyInfo(entity.getId(), entity.getAcademyName(), entity.getFullAddress());
    }

}
