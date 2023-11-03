package org.guzzing.studayserver.global.page;

import lombok.Getter;

@Getter
public class StudayPageRequest {

    private static final int DEFAULT_PAGE_SIZE = 10;

    private final int pageNumber;
    private final int pageSize;

    public StudayPageRequest(int pageNumber) {
        this.pageNumber = pageNumber;
        this.pageSize = DEFAULT_PAGE_SIZE;
    }

}
