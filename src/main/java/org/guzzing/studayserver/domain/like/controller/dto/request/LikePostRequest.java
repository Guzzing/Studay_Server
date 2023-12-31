package org.guzzing.studayserver.domain.like.controller.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.guzzing.studayserver.domain.like.service.dto.request.LikePostParam;
import org.springframework.validation.annotation.Validated;

public record LikePostRequest(
        @NotNull @Positive Long academyId
) {

    public static LikePostParam to(@Valid final LikePostRequest request,  final Long memberId) {
        return new LikePostParam(memberId, request.academyId());
    }

}
