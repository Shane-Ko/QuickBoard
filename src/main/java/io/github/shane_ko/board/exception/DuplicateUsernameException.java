package io.github.shane_ko.board.exception;

public class DuplicateUsernameException extends RuntimeException {

    private final ErrorCode errorCode;

    public DuplicateUsernameException(String username) {
        super(ErrorCode.DUPLICATE_USERNAME.getMessage() + " username = " + username);
        this.errorCode = ErrorCode.DUPLICATE_USERNAME;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }


}
