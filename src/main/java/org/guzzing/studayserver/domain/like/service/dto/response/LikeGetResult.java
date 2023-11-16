package org.guzzing.studayserver.domain.like.service.dto.response;

import java.util.List;

public record LikeGetResult(
        List<LikedAcademyFeeInfo> likeAcademyInfos,
        long totalFee
) {

    public static LikeGetResult of(final List<LikedAcademyFeeInfo> likeAcademyInfos, final long totalFee) {
        return new LikeGetResult(likeAcademyInfos, totalFee);
    }

}
