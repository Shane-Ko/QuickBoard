package io.github.shane_ko.board.exception;

public class MemberNotFoundException extends RuntimeException {

    private final ErrorCode errorCode;

    public MemberNotFoundException(Long id) {
        super(ErrorCode.MEMBER_NOT_FOUND.getMessage() + " id= " + id);
        this.errorCode = ErrorCode.MEMBER_NOT_FOUND;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
