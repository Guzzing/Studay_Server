package org.guzzing.studayserver.domain.academy.service.dto.param;

public record AcademiesByNameParam(
        String academyName,
        int pageNumber
) {

    public static AcademiesByNameParam of(String academyName,
                                          int pageNumber) {
        return new AcademiesByNameParam(academyName, pageNumber);
    }

}
