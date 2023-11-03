package org.guzzing.studayserver.domain.like.controller.dto.request;

import jakarta.validation.constraints.Positive;
import org.guzzing.studayserver.domain.like.service.dto.request.LikePostParam;

public record LikePostRequest(
        @Positive Long academyId
) {

    public static LikePostParam to(final LikePostRequest request, final Long memberId) {
        return new LikePostParam(memberId, request.academyId());
    }

}
