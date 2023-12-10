package org.guzzing.studayserver.domain.like.service.dto.response;

import java.util.List;

public record LikeGetResult(
        List<LikedAcademyFeeInfo> likeAcademyInfos,
        long totalFee
) {

    public static LikeGetResult of(final List<LikedAcademyFeeInfo> likedAcademyFeeInfos) {
        return new LikeGetResult(likedAcademyFeeInfos, getTotalFee(likedAcademyFeeInfos));
    }

    private static long getTotalFee(final List<LikedAcademyFeeInfo> likeAcademyFeeInfos) {
        return likeAcademyFeeInfos.stream()
                .mapToLong(LikedAcademyFeeInfo::expectedFee)
                .sum();
    }

}
