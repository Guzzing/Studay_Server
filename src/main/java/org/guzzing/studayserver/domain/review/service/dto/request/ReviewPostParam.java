package org.guzzing.studayserver.domain.review.service.dto.request;

public record ReviewPostParam(
        long memberId,
        long academyId,
        boolean kindness,
        boolean cheapFee,
        boolean goodFacility,
        boolean goodManagement,
        boolean lovelyTeaching,
        boolean shuttleAvailability
) {

}
