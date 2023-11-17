package org.guzzing.studayserver.domain.like.service.dto.response;

import java.util.List;

public record LikeGetResult(
        List<AcademyFeeInfo> likeAcademyInfos,
        long totalFee
) {

    public static LikeGetResult of(final List<AcademyFeeInfo> likeAcademyInfos, final long totalFee) {
        return new LikeGetResult(likeAcademyInfos, totalFee);
    }

}
