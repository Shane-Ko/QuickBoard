package io.github.shane_ko.board.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

    // 게시글 관련
    ARTICLE_NOT_FOUND(HttpStatus.NOT_FOUND,"요청하신 게시글을 찾을 수 없습니다."),
    // 서버 오류 (FallBack 용)
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류가 발생했습니다."),

    // 검증 관련
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST,"입력값이 올바르지 않습니다"),

    // 댓글 관련
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND,"요청하신 댓글을 찾을 수 없습니다.");


    /*
    Enum은 불변이 원칙이므로 필드에 final을 붙여 컴파일러가 불변성을 강제하도록 함.
    실수로 setter를 추가하거나 값을 변경하려는 시도를 원천 차단할 수 있음."
     */
    private final HttpStatus status;
    private final String message;



    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
