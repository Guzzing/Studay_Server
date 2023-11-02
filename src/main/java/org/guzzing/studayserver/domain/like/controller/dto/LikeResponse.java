package org.guzzing.studayserver.domain.like.controller.dto;

import org.guzzing.studayserver.domain.like.service.dto.LikeResult;

public record LikeResponse(
        Long likeId,
        Long memberId,
        Long academyId
) {

    public static LikeResponse from(final LikeResult result) {
        return new LikeResponse(result.likeId(), result.memberId(), result.academyId());
    }

}
