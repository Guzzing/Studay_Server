package org.guzzing.studayserver.domain.calendar_info.service.result;

import java.time.LocalTime;
import org.guzzing.studayserver.domain.calendar.service.dto.result.AcademyScheduleFindByDateResults.AcademyScheduleFindByDateResult;
import org.guzzing.studayserver.domain.member.service.result.MemberInformationResult.MemberChildInformationResult;

public record ChildAcademyScheduleCombineResult(
        Long childId,
        String childImageUrl,
        Long academyScheduleId,
        LocalTime startTime,
        LocalTime endTime,
        Long dashboardId
) {

    public static ChildAcademyScheduleCombineResult of(MemberChildInformationResult child,
            AcademyScheduleFindByDateResult academySchedule) {
        return new ChildAcademyScheduleCombineResult(
                child.childId(),
                child.profileImageUrlPath(),
                academySchedule.academyScheduleId(),
                academySchedule.startTime(),
                academySchedule.endTime(),
                academySchedule.dashboardId()
        );
    }
}
