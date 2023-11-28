package org.guzzing.studayserver.domain.academy.util;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum CategoryInfo {
    MATH(1L, "수학"),
    SCIENCE(2L, "과학"),
    KOREAN_LANGUAGE(3L, "국어"),
    ENGLISH(4L, "영어"),
    COMPUTER(5L, "컴퓨터"),
    ARTS_AND_PHYSICAL_EDUCATION(6L, "예체능"),
    FOREIGN_LANGUAGE(7L, "외국어"),
    TUTORING_SCHOOL(8L, "보습"),
    ETC(9L, "기타");

    private final Long id;

    private final String categoryName;

    CategoryInfo(Long id, String categoryName) {
        this.id = id;
        this.categoryName = categoryName;
    }

    public static String getCategoryNameById(Long id) {
        return Arrays.stream(CategoryInfo.values())
                .filter(categoryInfo -> categoryInfo.getId() == id)
                .map(categoryInfo -> categoryInfo.getCategoryName())
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("일치하는 카테고리명이 없습니다."));
    }

    public static Long getCategoryIdByName(String categoryName) {
        return Arrays.stream(CategoryInfo.values())
                .filter(categoryInfo -> categoryInfo.getCategoryName().equals(categoryName))
                .map(categoryInfo -> categoryInfo.getId())
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("일치하는 카테고리 아이디가 없습니다."));
    }

}
