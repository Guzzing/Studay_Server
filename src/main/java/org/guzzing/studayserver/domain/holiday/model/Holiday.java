package org.guzzing.studayserver.domain.holiday.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "holidays")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Holiday {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_name", nullable = false)
    private String dateName;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    public Holiday(LocalDate date, String dateName) {
        this.dateName = dateName;
        this.date = date;
    }

    public String getDateName() {
        return dateName;
    }

    public LocalDate getDate() {
        return date;
    }
}
