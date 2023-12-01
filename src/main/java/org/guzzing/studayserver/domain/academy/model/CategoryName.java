package org.guzzing.studayserver.domain.academy.model;

import lombok.Getter;

@Getter
public enum CategoryName {
    MATH(1L),
    SCIENCE(2L),
    KOREAN_LANGUAGE(3L),
    ENGLISH(4L),
    COMPUTER(5L),
    ARTS_AND_PHYSICAL_EDUCATION(6L),
    FOREIGN_LANGUAGE(7L),
    TUTORING_SCHOOL(8L),
    ETC(9L);

    private final Long id;

    CategoryName(Long id) {
        this.id = id;
    }
}
