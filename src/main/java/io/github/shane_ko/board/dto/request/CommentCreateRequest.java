package io.github.shane_ko.board.dto.request;

import io.github.shane_ko.board.entity.Comment;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/*
    * @NotBlank 만을 쓴다고 공백 입력을 막을 수 있는 것은 아님
    * 호출하는 곳에서 @Valid 어노테이션을 사용해야함
 */
@Getter
@NoArgsConstructor
@ToString
public class CommentCreateRequest {

    // TODO : writer 는 인증기능 추가시 제거
    @NotBlank(message = "댓글 작성자는 필수 입니다.")
    private String writer;
    @NotBlank(message = "댓글 내용은 필수 입니다.")
    private String content;

    public CommentCreateRequest(String writer, String content) {
        this.writer = writer;
        this.content = content;
    }
}
