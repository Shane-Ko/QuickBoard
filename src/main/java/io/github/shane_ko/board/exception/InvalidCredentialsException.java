package io.github.shane_ko.board.exception;

public class InvalidCredentialsException extends RuntimeException {

    private final ErrorCode errorCode = ErrorCode.INVALID_CREDENTIALS;

    public InvalidCredentialsException() {
            super(ErrorCode.INVALID_CREDENTIALS.getMessage());
    }
}
