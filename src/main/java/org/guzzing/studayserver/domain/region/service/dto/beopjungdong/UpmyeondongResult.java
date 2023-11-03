package org.guzzing.studayserver.domain.region.service.dto.beopjungdong;

import java.util.List;

public record UpmyeondongResult(
        String sido,
        String sigungu,
        List<String> upmyeondongs,
        int upmyeondongCount
) {

    public static UpmyeondongResult from(final String sido, final String sigungu, final List<String> upmyeondong) {
        return new UpmyeondongResult(sido, sigungu, upmyeondong, upmyeondong.size());
    }

}
