package io.github.shane_ko.board.exception;

public class CommentNotFoundException extends RuntimeException {

    private final ErrorCode errorCode;

    public CommentNotFoundException(Long id) {
        super(ErrorCode.COMMENT_NOT_FOUND.getMessage() + " id= " + id);
        this.errorCode = ErrorCode.COMMENT_NOT_FOUND;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
