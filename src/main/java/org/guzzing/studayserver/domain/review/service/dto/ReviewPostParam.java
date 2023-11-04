package org.guzzing.studayserver.domain.review.service.dto;

public record ReviewPostParam(
        Long memberId,
        Long academyId,
        boolean kindness,
        boolean cheapFee,
        boolean goodFacility,
        boolean goodManagement,
        boolean lovelyTeaching
) {

}
