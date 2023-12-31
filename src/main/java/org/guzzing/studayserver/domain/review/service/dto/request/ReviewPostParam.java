package org.guzzing.studayserver.domain.review.service.dto.request;

public record ReviewPostParam(
        Long memberId,
        Long academyId,
        Boolean kindness,
        Boolean cheapFee,
        Boolean goodFacility,
        Boolean goodManagement,
        Boolean lovelyTeaching,
        Boolean shuttleAvailability
) {

}
