package org.guzzing.studayserver.domain.like.service.dto.response;

import java.util.List;

public record LikeGetResult(
        List<LikedAcademyFeeInfo> likeAcademyInfos,
        long totalFee
) {

    private static final long DEFAULT_TOTAL_FEE = 0L;

    public static LikeGetResult of(final List<LikedAcademyFeeInfo> likedAcademyFeeInfos) {
        return new LikeGetResult(likedAcademyFeeInfos, getTotalFee(likedAcademyFeeInfos));
    }

    private static long getTotalFee(final List<LikedAcademyFeeInfo> likeAcademyFeeInfos) {
        try {
            return likeAcademyFeeInfos.stream()
                    .filter(likedAcademyFeeInfo -> likedAcademyFeeInfo.expectedFee() != null)
                    .mapToLong(LikedAcademyFeeInfo::expectedFee)
                    .sum();
        } catch (NullPointerException e) {
            return DEFAULT_TOTAL_FEE;
        }
    }

}
