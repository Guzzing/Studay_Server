package org.guzzing.studayserver.domain.calendar.exception;

import org.guzzing.studayserver.global.error.response.ErrorCode;

import java.util.Set;

public class DateOverlapException extends RuntimeException {

    private final ErrorCode errorCode;

    public DateOverlapException(final ErrorCode errorCode, Set<Long> dashboardIds) {
        super(String.format("중복된 시간표:{} {}", dashboardIds, errorCode.getMessage()));
        this.errorCode = errorCode;
    }
}
