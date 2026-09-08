package io.github.shane_ko.board.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberCreateRequest {

    @NotBlank(message = "아이디는 필수 입력 입니다.")
    private String username;
    @NotBlank(message = "닉네임은 필수 입력 입니다.")
    private String nickname;
    @NotBlank(message = "비밀번호는 필수 입력 입니다.")
    private String password;

    public MemberCreateRequest(String username, String nickname, String password) {
        this.username = username;
        this.nickname = nickname;
        this.password = password;
    }

}
