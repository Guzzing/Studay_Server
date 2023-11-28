package org.guzzing.studayserver.domain.academy.controller.dto.response;

import java.util.List;
import org.guzzing.studayserver.domain.academy.service.dto.result.AcademyFilterResult;

public record AcademyFilterResponse(
        Long academyId,
        String academyName,
        String address,
        String contact,
        List<String> categories,
        Double latitude,
        Double longitude,
        String shuttleAvailable,
        boolean isLiked
) {

    public static AcademyFilterResponse from(AcademyFilterResult academyFilterResult) {
        return new AcademyFilterResponse(
                academyFilterResult.academyId(),
                academyFilterResult.academyName(),
                academyFilterResult.fullAddress(),
                academyFilterResult.contact(),
                academyFilterResult.categories(),
                academyFilterResult.latitude(),
                academyFilterResult.longitude(),
                academyFilterResult.shuttleAvailable(),
                academyFilterResult.isLiked()
        );
    }
}
