package org.guzzing.studayserver.domain.calendar.repository.dto;

import java.time.LocalTime;
import java.util.Objects;

import lombok.Getter;

@Getter
public class AcademyCalenderDetailInfo {

    private final Long dashboardId;
    private final Long childId;
    private final LocalTime lessonStartTime;
    private final LocalTime lessonEndTime;
    private final String memo;

    public AcademyCalenderDetailInfo(Long dashboardId, Long childId, LocalTime lessonStartTime, LocalTime lessonEndTime,
                                     String memo) {
        this.dashboardId = dashboardId;
        this.childId = childId;
        this.lessonStartTime = lessonStartTime;
        this.lessonEndTime = lessonEndTime;
        this.memo = memo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AcademyCalenderDetailInfo that = (AcademyCalenderDetailInfo) o;
        return Objects.equals(dashboardId, that.dashboardId) && Objects.equals(childId, that.childId) && Objects.equals(
                lessonStartTime, that.lessonStartTime) && Objects.equals(lessonEndTime, that.lessonEndTime)
                && Objects.equals(memo, that.memo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dashboardId, childId, lessonStartTime, lessonEndTime, memo);
    }

}
