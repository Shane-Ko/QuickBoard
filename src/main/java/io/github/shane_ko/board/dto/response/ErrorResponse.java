package io.github.shane_ko.board.dto.response;

import io.github.shane_ko.board.exception.ErrorCode;

import java.time.LocalDateTime;

public class ErrorResponse {
    private final String code;
    private final String message;
    private final int status;
    private final LocalDateTime timestamp;
    private final String path;

    private ErrorResponse(String code, String message, int status, LocalDateTime timestamp, String path) {
        this.code = code;
        this.message = message;
        this.status = status;
        this.timestamp = timestamp;
        this.path = path;
    }

    // 정적 팩토리 메서드 - ErrorCode 랑 path 만 받아서 나머지는 자동으로 채움
    public static ErrorResponse of (ErrorCode errorCode, String path) {
        return new ErrorResponse(
                errorCode.name(),
                errorCode.getMessage(),
                errorCode.getStatus().value(),
                LocalDateTime.now(),
                path
        );
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getPath() {
        return path;
    }
}
