package io.github.shane_ko.board.exception;

public class ArticleNotFoundException extends RuntimeException {

    private final ErrorCode errorCode;

    public ArticleNotFoundException (Long id) {
        super(ErrorCode.ARTICLE_NOT_FOUND.getMessage() + " id= " + id);
        this.errorCode = ErrorCode.ARTICLE_NOT_FOUND;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
