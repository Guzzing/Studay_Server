package org.guzzing.studayserver.domain.child.model;

import java.util.Arrays;
import lombok.Getter;

@Getter
public enum Grade {
    ELEMENTARY_SCHOOL_1(0, "초등학교 1학년"),
    ELEMENTARY_SCHOOL_2(1, "초등학교 2학년"),
    ELEMENTARY_SCHOOL_3(2, "초등학교 3학년"),
    ELEMENTARY_SCHOOL_4(3, "초등학교 4학년"),
    ELEMENTARY_SCHOOL_5(4, "초등학교 5학년"),
    ELEMENTARY_SCHOOL_6(5, "초등학교 6학년"),
    MIDDLE_SCHOOL_1(6, "중학교 1학년"),
    MIDDLE_SCHOOL_2(7, "중학교 2학년"),
    MIDDLE_SCHOOL_3(8, "중학교 3학년"),
    HIGH_SCHOOL_1(9, "고등학교 1학년"),
    HIGH_SCHOOL_2(10, "고등학교 2학년"),
    HIGH_SCHOOL_3(11, "고등학교 3학년"),
    GRADUATED(12, "성인");

    private final int gradeLevel;
    private final String description;

    Grade(int gradeLevel, String description) {
        this.gradeLevel = gradeLevel;
        this.description = description;
    }

    public static Grade increaseGrade(final Grade grade) {
        int level = grade.getGradeLevel();

        return ++ level >= Grade.values().length
                ? GRADUATED
                : Grade.values()[level];
    }

    public static Grade fromDescription(String description) {
        return Arrays.stream(Grade.values())
                .filter(grade -> grade.getDescription().equals(description))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 학년입니다: " + description));
    }

}





