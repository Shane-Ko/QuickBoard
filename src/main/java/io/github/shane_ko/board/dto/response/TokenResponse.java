package io.github.shane_ko.board.dto.response;

import io.github.shane_ko.board.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TokenResponse {

    private final String token;
    private final String nickname;

    public static TokenResponse of(String token, Member member) {
        return new TokenResponse(token, member.getNickname());
    }
}
