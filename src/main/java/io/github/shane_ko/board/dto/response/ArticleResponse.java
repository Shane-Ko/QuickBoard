package io.github.shane_ko.board.dto.response;

import io.github.shane_ko.board.entity.Article;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
@Getter
@AllArgsConstructor
public class ArticleResponse {

    private Long id;
    private String title;
    private String content;
    private String writer;
    private LocalDateTime createdAt;

    // Entity -> DTO 변환 로직
    public static ArticleResponse from (Article article) {
        return new ArticleResponse(
                article.getId(),
                article.getTitle(),
                article.getContent(),
                article.getMember().getNickname(),
                article.getCreatedAt()
        );
    }


}
