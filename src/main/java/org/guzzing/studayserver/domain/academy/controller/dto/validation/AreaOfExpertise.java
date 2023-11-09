package org.guzzing.studayserver.domain.academy.controller.dto.validation;

public enum AreaOfExpertise {

    FOREIGN_LANGUAGE("국제화"),
    DANCE("기예(대)"),
    ETC("기타(대)"),
    READING_ROOM("독서실"),
    ART_AND_MUSIC("예능(대)"),
    HUMANITIES_AND_HISTORY("인문사회(대)"),
    TUTORING_SCHOOL("입시.검정 및 보습"),
    COMPREHENSIVE("종합(대)");

    private String kind;

    AreaOfExpertise(String kind) {
        this.kind = kind;
    }

    public String getAreaOfExpertise() {
        return kind;
    }

}
