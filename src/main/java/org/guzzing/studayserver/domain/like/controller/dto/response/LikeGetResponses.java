package org.guzzing.studayserver.domain.like.controller.dto.response;

import java.util.List;
import org.guzzing.studayserver.domain.like.service.dto.response.AcademyFeeInfo;
import org.guzzing.studayserver.domain.like.service.dto.response.LikeGetResult;

public record LikeGetResponses(
        List<AcademyFeeInfo> likeAcademyInfos,
        long totalFee
) {

    public static LikeGetResponses from(final LikeGetResult result) {
        return new LikeGetResponses(result.likeAcademyInfos(), result.totalFee());
    }

}
