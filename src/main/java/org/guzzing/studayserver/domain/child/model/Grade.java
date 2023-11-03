package org.guzzing.studayserver.domain.child.model;

import java.util.Arrays;
import lombok.Getter;

@Getter
public enum Grade {
    ELEMENTARY_SCHOOL_1("초등학교 1학년"),
    ELEMENTARY_SCHOOL_2("초등학교 2학년"),
    ELEMENTARY_SCHOOL_3("초등학교 3학년"),
    ELEMENTARY_SCHOOL_4("초등학교 4학년"),
    ELEMENTARY_SCHOOL_5("초등학교 5학년"),
    ELEMENTARY_SCHOOL_6("초등학교 6학년"),
    MIDDLE_SCHOOL_1("중학교 1학년"),
    MIDDLE_SCHOOL_2("중학교 2학년"),
    MIDDLE_SCHOOL_3("중학교 3학년"),
    HIGH_SCHOOL_1("고등학교 1학년"),
    HIGH_SCHOOL_2("고등학교 2학년"),
    HIGH_SCHOOL_3("고등학교 3학년");

    private final String description;

    public static Grade fromDescription(String description) {
        return Arrays.stream(Grade.values())
                .filter(grade -> grade.getDescription().equals(description))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 학년입니다: " + description));
    }

    Grade(String description) {
        this.description = description;
    }
}





