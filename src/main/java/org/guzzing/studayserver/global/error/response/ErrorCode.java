package org.guzzing.studayserver.global.error.response;

import lombok.Getter;

@Getter
public enum ErrorCode {
    //global
    INTERNAL_SERVER_ERROR("G001", "Internal Server Error"),

    ILLEGAL_ARGUMENT_ERROR("E001", "잘못된 입력입니다."),
    ILLEGAL_STATE_ERROR("E002", "잘못된 상태입니다"),


    INVALID_INPUT_VALUE_ERROR("G002", "유효하지 않은 입력값입니다."),
    INVALID_METHOD_ERROR("G003", "Method Argument가 적절하지 않습니다."),
    REQUEST_BODY_MISSING_ERROR("G004", "RequestBody에 데이터가 존재하지 않습니다."),
    REQUEST_PARAM_MISSING_ERROR("G005", "RequestParam에 데이터가 전달되지 않았습니다."),
    INVALID_TYPE_VALUE_ERROR("G006", "타입이 유효하지 않습니다."),
    NOT_FOUND_ENTITY("G007", "엔티티를 찾을 수 없습니다."),
    UTIL_NOT_CONSTRUCTOR("G008","유틸클래스는 생성자를 호출할 수 없습니다."),


    //login
    EXPIRED_TOKEN("L001", "토큰이 만료되었습니다."),
    EXPIRED_REFRESH_TOKEN("L002", "리프레쉬 토큰도 만료되어 다시 로그인을 요청합니다."),
    UNAUTHORIZED_TOKEN("L003", "인증되지 않은 토큰입니다."),
    OAUTH_CLIENT_SERVER_ERROR("L004", "oauth 클라이언트 서버 에러입니다."),
    IS_LOGOUT_TOKEN("L005","이미 로그아웃한 토큰입니다.");


    private final String code;
    private final String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

}
