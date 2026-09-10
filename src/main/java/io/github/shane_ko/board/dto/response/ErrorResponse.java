package io.github.shane_ko.board.dto.response;

import io.github.shane_ko.board.exception.ErrorCode;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

public class ErrorResponse {
    private final String code;
    private final String message;
    private final int status;
    private final LocalDateTime timestamp;
    private final String path;
    private final List<FieldError> errors;    // ★ 추가

    private ErrorResponse(String code, String message, int status, LocalDateTime timestamp,
                          String path, List<FieldError> errors) {
        this.code = code;
        this.message = message;
        this.status = status;
        this.timestamp = timestamp;
        this.path = path;
        this.errors = errors;
    }

    // 필요 에러 없을 시
    public static ErrorResponse of(ErrorCode errorCode, String path) {
        return new ErrorResponse(
                errorCode.name(),
                errorCode.getMessage(),
                errorCode.getStatus().value(),
                LocalDateTime.now(),
                path,
                Collections.emptyList()
        );
    }

    // 필드 에러 있을 시 - validation 실패용
    public static ErrorResponse of(ErrorCode errorCode, String path, List<FieldError> errors) {
        return new ErrorResponse(
                errorCode.name(),
                errorCode.getMessage(),
                errorCode.getStatus().value(),
                LocalDateTime.now(),
                path,
                errors
        );
    }

    public String getCode() { return code; }
    public String getMessage() { return message; }
    public int getStatus() { return status; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public String getPath() { return path; }
    public List<FieldError> getErrors() { return errors; }    // ★ 추가

    // 필드별 에러 정보
    public static class FieldError {
        private final String field;
        private final String value;
        private final String reason;

        public FieldError(String field, String value, String reason) {
            this.field = field;
            this.value = value;
            this.reason = reason;
        }

        public String getField() { return field; }
        public String getValue() { return value; }
        public String getReason() { return reason; }
    }
}