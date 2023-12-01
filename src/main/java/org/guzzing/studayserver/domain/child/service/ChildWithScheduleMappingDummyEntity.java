package org.guzzing.studayserver.domain.child.service;

import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SqlResultSetMapping;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Getter;

@Getter
@Entity
@SqlResultSetMapping(
        name = "ChildWithScheduleResultSetMapping",
        classes = @ConstructorResult(
                targetClass = ChildDateScheduleResult.class,
                columns = {
                        @ColumnResult(name = "child_id", type = Long.class),
                        @ColumnResult(name = "schedule_date", type = LocalDate.class),
                        @ColumnResult(name = "lesson_start_time", type = LocalTime.class),
                        @ColumnResult(name = "lesson_end_time", type = LocalTime.class),
                        @ColumnResult(name = "academy_name", type = String.class),
                        @ColumnResult(name = "lesson_subject", type = String.class)
                }
        )
)
public class ChildWithScheduleMappingDummyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
