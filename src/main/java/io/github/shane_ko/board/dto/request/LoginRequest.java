package io.github.shane_ko.board.dto.request;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginRequest {

    @NotBlank(message = "아이디는 필수 입력 입니다.")
    private String username;

    @NotBlank(message = "비밀번호는 필수 입력 입니다.")
    private String password;

    /*
        * 생성자는 프로덕션 로직에서는 사용하지 않지만
        * 테스트 코드에서 사용하는 목적으로 만들어놓았음
     */
    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
