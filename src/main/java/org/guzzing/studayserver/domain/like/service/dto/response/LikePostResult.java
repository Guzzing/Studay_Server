package org.guzzing.studayserver.domain.like.service.dto.response;

import org.guzzing.studayserver.domain.like.model.Like;

public record LikePostResult(
        Long likeId,
        Long memberId,
        Long academyId
) {

    public static LikePostResult from(final Like entity) {
        return new LikePostResult(entity.getId(), entity.getMemberId(), entity.getAcademyId());
    }

}
