package org.guzzing.studayserver.domain.like.service.dto;

import org.guzzing.studayserver.domain.like.model.Like;

public record LikeResult(
        Long likeId,
        Long memberId,
        Long academyId
) {

    public static LikeResult from(final Like entity) {
        return new LikeResult(entity.getId(), entity.getMemberId(), entity.getAcademyId());
    }

}
