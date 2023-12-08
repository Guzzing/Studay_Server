package org.guzzing.studayserver.domain.academy.repository.dto;

import java.util.List;

public record AcademiesByLocationWithScroll(
        List<AcademyByLocationWithScroll> academiesByLocation,
        Long beforeLastId,
        boolean hasNext
) {
    public static AcademiesByLocationWithScroll of(
            List<AcademyByLocationWithScroll> academiesByLocation,
            Long beforeLastId,
            boolean hasNext
    ) {
        return new AcademiesByLocationWithScroll(
                academiesByLocation,
                beforeLastId,
                hasNext
        );
    }

}
