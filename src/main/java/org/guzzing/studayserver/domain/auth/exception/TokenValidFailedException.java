package org.guzzing.studayserver.domain.auth.exception;

import org.guzzing.studayserver.global.error.response.ErrorCode;
import lombok.Getter;

@Getter
public class TokenValidFailedException extends IllegalStateException {

    private final ErrorCode errorCode;

    public TokenValidFailedException(final ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

}
