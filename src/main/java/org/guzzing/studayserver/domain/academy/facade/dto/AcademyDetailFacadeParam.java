package org.guzzing.studayserver.domain.academy.facade.dto;

public record AcademyDetailFacadeParam(
        Long memberId,
        Long academyId
) {

    public static AcademyDetailFacadeParam of(Long memberId, Long academyId) {
        return new AcademyDetailFacadeParam(
                memberId,
                academyId
        );
    }
}
