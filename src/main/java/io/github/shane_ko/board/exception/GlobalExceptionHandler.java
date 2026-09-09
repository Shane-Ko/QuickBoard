package io.github.shane_ko.board.exception;

import io.github.shane_ko.board.dto.response.ErrorResponse;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Fallback;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // 1. ArticleNotFoundException 처리 (404)
    @ExceptionHandler(ArticleNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlerArticleNotFound(
            ArticleNotFoundException e,     // 던져진 예외 객체
            HttpServletRequest request) {   // Spring이 자동으로 요청 정보 넘겨줌

        // 로그 남기기 (개발자용)
        log.warn("[ArticleNotFound] {} - path: {}", e.getMessage(), request.getRequestURI());

        // ErrorResponse 조립
        ErrorResponse response = ErrorResponse.of(e.getErrorCode(), request.getRequestURI());

        // 응답반환 (상태 코드 + body)
        return ResponseEntity
                .status(e.getErrorCode().getStatus())
                .body(response);
    }

    // CommentNotFoundException (404)
    @ExceptionHandler(CommentNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlerCommentNotFound(
            CommentNotFoundException e,
            HttpServletRequest request) {
        // 로그 남기기
        log.warn("[CommentNotFound] {} - path: {}", e.getMessage(), request.getRequestURI());

        // ErrorResponse 조립
        ErrorResponse response = ErrorResponse.of(e.getErrorCode(), request.getRequestURI());

        // 응답반환 (상태코드 + body)
        return ResponseEntity
                .status(e.getErrorCode().getStatus())
                .body(response);
    }

    // 예상치 못한 모든 예외 처리 (500) - Fallback
    @ExceptionHandler(Exception.class)  // 모든 예외의 조상
    public ResponseEntity<ErrorResponse> handleAll(
            Exception e,
            HttpServletRequest request) {

        // 로그 남기기
        log.warn("[Unexpected Error]", e);

        // ErrorCode 는 뭘 쓸지
        ErrorResponse response = ErrorResponse.of(
                ErrorCode.INTERNAL_SERVER_ERROR
                , request.getRequestURI()
        );

        return ResponseEntity
                .status(ErrorCode.INTERNAL_SERVER_ERROR.getStatus())
                .body(response);
    }

    // 댓글 작성시 공백에 대한 예외 처리 (400)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handlerArgumentNotValid(
            MethodArgumentNotValidException e,
            HttpServletRequest request) {

        // 로그 남기기
        log.warn("[MethodArgumentNotValid] {} - path : {}", e.getMessage(), request.getRequestURI());

        // ErrorResponse 조립
        ErrorResponse response = ErrorResponse.of(
                ErrorCode.INVALID_INPUT_VALUE,
                request.getRequestURI()
        );
        return ResponseEntity.status(ErrorCode.INVALID_INPUT_VALUE.getStatus())
                .body(response);
    }

    // 중복 아이디 오류 (409)
    @ExceptionHandler(DuplicateUsernameException.class)
    public ResponseEntity<ErrorResponse> handlerDuplicateUsername(
            DuplicateUsernameException e,
            HttpServletRequest request) {

        // 로그
        log.warn("[DuplicateUsername] {} - path : {}", e.getMessage(), request.getRequestURI());

        // ErrorResponse 조립
        ErrorResponse response = ErrorResponse.of(
                ErrorCode.DUPLICATE_USERNAME,
                request.getRequestURI()
        );
        return ResponseEntity.status(ErrorCode.DUPLICATE_USERNAME.getStatus())
                .body(response);
    }

    // 중복 닉네임 오류(409)
    @ExceptionHandler(DuplicateNicknameException.class)
    public ResponseEntity<ErrorResponse> handlerDuplicateNickname(
            DuplicateNicknameException e,
            HttpServletRequest request) {

        log.warn("[DuplicateNickname] {} - path : {}", e.getMessage(), request.getRequestURI());

        ErrorResponse response = ErrorResponse.of(
                ErrorCode.DUPLICATE_NICKNAME,
                request.getRequestURI()
        );
        return ResponseEntity.status(ErrorCode.DUPLICATE_NICKNAME.getStatus())
                .body(response);
    }

    // 아이디 혹은 비밀번호 일치 실패
    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorResponse> handlerInvalidCredentials(
            InvalidCredentialsException e,
            HttpServletRequest request) {
        log.warn("[InvalidCredentials]");

        ErrorResponse response = ErrorResponse.of(
                ErrorCode.INVALID_CREDENTIALS,
                request.getRequestURI()
        );

        return ResponseEntity.status(ErrorCode.INVALID_CREDENTIALS.getStatus())
                .body(response);
    }

    // 사용자를 찾을 수 없음
    @ExceptionHandler(MemberNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlerMemberNotFound(
            MemberNotFoundException e,
            HttpServletRequest request) {
        log.warn("[MemberNotFound] {} - path: {}", e.getMessage(), request.getRequestURI());

        ErrorResponse response = ErrorResponse.of(
                ErrorCode.MEMBER_NOT_FOUND,
                request.getRequestURI()
        );

        return ResponseEntity.status(ErrorCode.MEMBER_NOT_FOUND.getStatus())
                .body(response);
    }

    // 접근 권한 예외 처리
    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ErrorResponse> handlerForbidden(
            ForbiddenException e,
            HttpServletRequest request) {
        log.warn("[Forbidden]");

        ErrorResponse response = ErrorResponse.of(
                ErrorCode.FORBIDDEN,
                request.getRequestURI()
        );

        return ResponseEntity.status(ErrorCode.FORBIDDEN.getStatus())
                .body(response);
    }
}
