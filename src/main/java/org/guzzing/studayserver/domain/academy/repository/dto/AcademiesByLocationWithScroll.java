package org.guzzing.studayserver.domain.academy.repository.dto;

import java.util.List;

public record AcademiesByLocationWithScroll(
        List<AcademyByLocationWithScroll> academiesByLocation,
        boolean hasNext
) {
    public static AcademiesByLocationWithScroll of(
            List<AcademyByLocationWithScroll> academiesByLocation,
            boolean hasNext
    ) {
        return new AcademiesByLocationWithScroll(
                academiesByLocation,
                hasNext
        );
    }

}
