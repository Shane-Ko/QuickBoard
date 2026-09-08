package io.github.shane_ko.board.dto.request;

import io.github.shane_ko.board.entity.Article;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/*
"Create와 Update는 요구하는 필드와 검증 조건이 다르다고 판단해 DTO를 분리
특히 writer 필드를 Update DTO에서 제외해 작성자 위조 위험을 차단"
 */
@Getter
@NoArgsConstructor
@ToString
public class ArticleUpdateRequest {
    @NotBlank(message = "제목은 필수 입니다.")
    private String title;
    @NotBlank(message = "내용은 필수 입니다.")
    private String content;
    // writer 는 수정 사항이 아니므로 없음


    public ArticleUpdateRequest(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
