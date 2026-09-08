package io.github.shane_ko.board.dto.request;

import io.github.shane_ko.board.entity.Article;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class ArticleCreateRequest {

    @NotBlank(message = "제목은 필수 입니다.")
    private String title;
    @NotBlank(message = "내용은 필수 입니다.")
    private String content;
    @NotBlank(message = "작성자는 필수 입니다.")
    // TODO : writer 는 인증기능 추가시 제거
    private String writer;

    public ArticleCreateRequest(String title, String content, String writer) {
        this.title = title;
        this.content = content;
        this.writer = writer;
    }

    public Article toEntity() {
        return new Article(title, content, writer);
    }

}
