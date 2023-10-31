package org.guzzing.studay.domain.auth.exception;

import org.guzzing.studay.global.error.response.ErrorCode;
import lombok.Getter;

@Getter
public class TokenValidFailedException extends IllegalStateException {

    private final ErrorCode errorCode;

    public TokenValidFailedException(final ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

}
