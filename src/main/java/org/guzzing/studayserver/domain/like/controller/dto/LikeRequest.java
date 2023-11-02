package org.guzzing.studayserver.domain.like.controller.dto;

import jakarta.validation.constraints.Positive;
import org.guzzing.studayserver.domain.like.service.dto.LikeParam;

public record LikeRequest(
        @Positive Long academyId
) {

    public static LikeParam to(final LikeRequest request, final Long memberId) {
        return new LikeParam(memberId, request.academyId());
    }

}
