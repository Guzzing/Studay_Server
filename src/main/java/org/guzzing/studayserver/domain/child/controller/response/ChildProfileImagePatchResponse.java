package org.guzzing.studayserver.domain.child.controller.response;

import org.guzzing.studayserver.domain.child.service.result.ChildProfileImagePatchResult;

public record ChildProfileImagePatchResponse(
        Long childId,
        String profileImageUrl
) {

    public static ChildProfileImagePatchResponse from(final ChildProfileImagePatchResult result) {
        return new ChildProfileImagePatchResponse(result.childId(), result.profileImageUrl());
    }

}
