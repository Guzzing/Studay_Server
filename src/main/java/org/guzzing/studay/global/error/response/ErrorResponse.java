package org.guzzing.studay.global.error.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Global Exception Rest Handler에서 발생한 에러에 대한 응답 처리를 관리
 */

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorResponse {
    private String code; //서버 내 에러코드
    private String message; //에러 메시지
    private List<FieldDetailError> fieldDetailErrors; //상세 에러 메시지
    private String reason; //에러 이유 - Exception 정보

    public static ErrorResponse of(final ErrorCode errorCode, final BindingResult bindingResult) {
        return new ErrorResponse(errorCode, FieldDetailError.of(bindingResult));
    }

    public static ErrorResponse of(ErrorCode errorCode) {
        return new ErrorResponse(errorCode);
    }

    public static ErrorResponse of(final ErrorCode errorCode, final String reason) {
        return new ErrorResponse(errorCode, reason);
    }

    private ErrorResponse(final ErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
        this.fieldDetailErrors = new ArrayList<>();
    }

    private ErrorResponse(final ErrorCode errorCode, final String reason) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
        this.reason = reason;
    }

    private ErrorResponse(final ErrorCode errorCode, final List<FieldDetailError> fieldDetailErrors) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
        this.fieldDetailErrors = fieldDetailErrors;
    }

    public static class FieldDetailError {
        private final String field;
        private final String value;
        private final String reason;

        public String getField() {
            return field;
        }

        public String getValue() {
            return value;
        }

        public String getReason() {
            return reason;
        }

        private static List<FieldDetailError> of(final BindingResult bindingResult) {
            final List<org.springframework.validation.FieldError> fieldErrors = bindingResult.getFieldErrors();
            return fieldErrors.stream()
                    .map(error -> new FieldDetailError(
                            error.getField(),
                            error.getRejectedValue() == null ? "" : error.getRejectedValue().toString(),
                            error.getDefaultMessage()))
                    .collect(Collectors.toList());
        }

        private FieldDetailError(String field, String value, String reason) {
            this.field = field;
            this.value = value;
            this.reason = reason;
        }
    }
}