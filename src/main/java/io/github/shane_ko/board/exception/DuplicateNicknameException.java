package io.github.shane_ko.board.exception;

public class DuplicateNicknameException extends RuntimeException {

    private final ErrorCode errorCode;

    public DuplicateNicknameException(String nickname) {
        super(ErrorCode.DUPLICATE_NICKNAME.getMessage() + " nickname = " + nickname);
        this.errorCode = ErrorCode.DUPLICATE_NICKNAME;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }


}
