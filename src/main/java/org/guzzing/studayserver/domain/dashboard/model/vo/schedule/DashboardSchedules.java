package org.guzzing.studayserver.domain.dashboard.model.vo.schedule;

import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Embeddable;
import java.util.List;
import java.util.Objects;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Embeddable
public final class DashboardSchedules {

    private List<Schedule> schedules;

    protected DashboardSchedules(
            final List<Schedule> schedules
    ) {
        this.schedules = schedules;
    }

    public static DashboardSchedules of(final List<Schedule> schedules) {
        return new DashboardSchedules(schedules);
    }

    public List<Schedule> schedules() {
        return schedules;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        var that = (DashboardSchedules) obj;
        return Objects.equals(this.schedules, that.schedules);
    }

    @Override
    public int hashCode() {
        return Objects.hash(schedules);
    }

    @Override
    public String toString() {
        return "DashboardSchedules[" +
                "schedules=" + schedules + ']';
    }

}
