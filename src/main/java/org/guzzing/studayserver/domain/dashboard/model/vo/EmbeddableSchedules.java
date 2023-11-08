package org.guzzing.studayserver.domain.dashboard.model.vo;

import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Embeddable;
import java.util.List;
import java.util.Objects;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Embeddable
public final class EmbeddableSchedules {

    private List<EmbeddableSchedule> schedules;

    public EmbeddableSchedules(final List<EmbeddableSchedule> schedules) {
        this.schedules = schedules;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        var that = (EmbeddableSchedules) obj;
        return Objects.equals(this.schedules, that.schedules);
    }

    @Override
    public int hashCode() {
        return Objects.hash(schedules);
    }

    @Override
    public String toString() {
        return "EmbeddableSchedules[" +
                "embeddableSchedules=" + schedules + ']';
    }

}
