package org.guzzing.studay.global.error.response;

import lombok.Getter;

@Getter
public enum ErrorCode {
    //global
    INTERNAL_SERVER_ERROR("G001", "Internal Server Error"),

    INVALID_INPUT_VALUE_ERROR("G002", "유효하지 않은 입력값입니다."),
    INVALID_METHOD_ERROR("G003", "Method Argument가 적절하지 않습니다."),
    REQUEST_BODY_MISSING_ERROR("G004", "RequestBody에 데이터가 존재하지 않습니다."),
    REQUEST_PARAM_MISSING_ERROR("G005", "RequestParam에 데이터가 전달되지 않았습니다."),
    INVALID_TYPE_VALUE_ERROR("G006", "타입이 유효하지 않습니다."),
    NOT_FOUND_ENTITY("G007", "엔티티를 찾을 수 없습니다."),

    //login
    EXPIRED_TOKEN("L001", "토큰이 만료되었습니다."),
    UNAUTHORIZED_TOKEN("L002", "인증되지 않은 토큰입니다."),
    OAUTH_CLIENT_SERVER_ERROR("L003", "oauth 클라이언트 서버 에러입니다.");

    private final String code;
    private final String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

}
