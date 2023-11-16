package org.guzzing.studayserver.domain.acdademycalendar.repository.dto;

import lombok.Getter;

import java.time.LocalDate;
import java.util.Objects;

@Getter
public class AcademyTimeTemplateInfoToUpdateDto{

    private Long id;
    private LocalDate startDateOfAttendance;
    private LocalDate endDateOfAttendance;
    private boolean isAlarmed;
    private String memo;
    private Long dashboardId;

    public AcademyTimeTemplateInfoToUpdateDto(
            Long id,
            LocalDate startDateOfAttendance,
            LocalDate endDateOfAttendance,
            boolean isAlarmed,
            String memo,
            Long dashboardId) {
        this.id = id;
        this.startDateOfAttendance = startDateOfAttendance;
        this.endDateOfAttendance = endDateOfAttendance;
        this.isAlarmed = isAlarmed;
        this.memo = memo;
        this.dashboardId = dashboardId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AcademyTimeTemplateInfoToUpdateDto that = (AcademyTimeTemplateInfoToUpdateDto) o;
        return isAlarmed == that.isAlarmed && Objects.equals(id, that.id) && Objects.equals(startDateOfAttendance, that.startDateOfAttendance) && Objects.equals(endDateOfAttendance, that.endDateOfAttendance) && Objects.equals(memo, that.memo) && Objects.equals(dashboardId, that.dashboardId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, startDateOfAttendance, endDateOfAttendance, isAlarmed, memo, dashboardId);
    }
}
