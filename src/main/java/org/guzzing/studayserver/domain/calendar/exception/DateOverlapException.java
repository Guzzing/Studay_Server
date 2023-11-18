package org.guzzing.studayserver.domain.calendar.exception;

import org.guzzing.studayserver.global.error.response.ErrorCode;

public class DateOverlapException extends RuntimeException {

    private final ErrorCode errorCode;

    public DateOverlapException(final ErrorCode errorCode, Long timeTemplateId) {
        super("중복된 시간표 :" + timeTemplateId + " " + errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
