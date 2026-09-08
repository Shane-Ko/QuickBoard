package io.github.shane_ko.board.dto.response;

import io.github.shane_ko.board.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CommentResponse {
    private final Long id;
    private final String writer;
    private final String content;
    private final LocalDateTime createdAt;
    private final Long articleId;

    // 정적 팩토리 메서드
    // Entity를 받아서 DTO 로 변환
    // 서버 -> 클라 응답 데이터 담는 곳
    public static CommentResponse from(Comment comment) {
        return new CommentResponse(
                comment.getId(),
                comment.getMember().getNickname(),
                comment.getContent(),
                comment.getCreatedAt(),
                comment.getArticle().getId()    // 관계에서 id만 꺼냄
        );
    }

}
