package org.guzzing.studayserver.domain.like.service.dto.response;

public record LikedAcademyFeeInfo(
        long likeId,
        long academyId,
        String academyName,
        long expectedFee
) {

}
