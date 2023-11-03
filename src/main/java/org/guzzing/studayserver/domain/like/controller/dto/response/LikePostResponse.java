package org.guzzing.studayserver.domain.like.controller.dto.response;

import org.guzzing.studayserver.domain.like.service.dto.LikeResult;

public record LikePostResponse(
        Long likeId,
        Long memberId,
        Long academyId
) {

    public static LikePostResponse from(final LikeResult result) {
        return new LikePostResponse(result.likeId(), result.memberId(), result.academyId());
    }

}
