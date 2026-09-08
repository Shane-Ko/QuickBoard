package io.github.shane_ko.board.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor  // Jackson 은 먼저 빈 객체를 만든 후, 값을 채움'
@Getter
public class CommentUpdateRequest {

    @NotBlank(message = "내용은 필수 입니다.")
    private String content;


    public CommentUpdateRequest(String content) {
        this.content = content;
    }
}
