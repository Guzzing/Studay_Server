package org.guzzing.studayserver.domain.like.controller.dto.request;

import jakarta.validation.constraints.Positive;
import org.guzzing.studayserver.domain.like.service.dto.LikeParam;

public record LikePostRequest(
        @Positive Long academyId
) {

    public static LikeParam to(final LikePostRequest request, final Long memberId) {
        return new LikeParam(memberId, request.academyId());
    }

}
