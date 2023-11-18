package org.guzzing.studayserver.domain.calendar.exception;

import org.guzzing.studayserver.global.error.response.ErrorCode;

public class NotGeneratedScheduleException extends RuntimeException {

    private final ErrorCode errorCode;

    public NotGeneratedScheduleException(final ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
