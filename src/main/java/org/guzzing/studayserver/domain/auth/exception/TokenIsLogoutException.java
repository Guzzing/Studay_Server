package org.guzzing.studayserver.domain.auth.exception;

import lombok.Getter;
import org.guzzing.studayserver.global.error.response.ErrorCode;

@Getter
public class TokenIsLogoutException extends RuntimeException {

    private final ErrorCode errorCode;

    public TokenIsLogoutException(final ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

}
