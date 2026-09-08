package io.github.shane_ko.board.dto.request;

import io.github.shane_ko.board.entity.Article;
import io.github.shane_ko.board.entity.Member;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
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

    private Member member;


    public ArticleCreateRequest(String title, String content, Member member) {
        this.title = title;
        this.content = content;
        this.member = member;
    }

    public Article toEntity() {
        return new Article(title, content, member);
    }

}
