package org.guzzing.studayserver.domain.academy.repository.dto;

import java.util.List;

public record AcademiesByFilterWithScroll(
        List<AcademyByFilterWithScroll> academiesByLocation,
        boolean hasNext
) {

    public static AcademiesByFilterWithScroll of(
            List<AcademyByFilterWithScroll> academiesByLocation,
            boolean hasNext
    ) {
        return new AcademiesByFilterWithScroll(
                academiesByLocation,
                hasNext
        );
    }

}
