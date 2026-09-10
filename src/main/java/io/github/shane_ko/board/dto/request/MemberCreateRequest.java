package io.github.shane_ko.board.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberCreateRequest {

    @NotBlank(message = "아이디는 필수 입력 입니다.")
    @Pattern(
            regexp = "^[a-zA-Z0-9]{4,20}$",
            message = "아이디는 영문/숫자 4~20자로 입력해주세요."
    )
    private String username;
    @NotBlank(message = "닉네임은 필수 입력 입니다.")
    @Pattern(
            regexp = "^[a-zA-Z0-9가-힣]{2,10}$",
            message = "닉네임은 영문/숫자/한글 2~10자로 입력해주세요."
    )
    private String nickname;
    @NotBlank(message = "비밀번호는 필수 입력 입니다.")
    @Pattern(
            regexp = "^.{8,20}$",
            message = "비밀번호는 8~20자로 입력해주세요."
    )
    private String password;


    /*
        * 생성자는 프로덕션 로직에서는 사용하지 않지만
        * 테스트 코드에서 사용하는 목적으로 만들어놓았음
     */
    public MemberCreateRequest(String username, String nickname, String password) {
        this.username = username;
        this.nickname = nickname;
        this.password = password;
    }

}
