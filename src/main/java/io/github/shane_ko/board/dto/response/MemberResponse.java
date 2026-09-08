package io.github.shane_ko.board.dto.response;

import io.github.shane_ko.board.entity.Member;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MemberResponse {

    private final Long id;
    private final String username;
    private final String nickname;
    private final LocalDateTime createdAt;

    public MemberResponse(Long id, String username, String nickname,
                          LocalDateTime createdAt) {
        this.id = id;
        this.username = username;
        this.nickname = nickname;
        this.createdAt = createdAt;
    }

    // 정적 팩토리 메서드
    // Entity 를 받아서 DTO 로 변환
    // 서버 -> 클라 응답 데이터 담는 곳
    public static MemberResponse from (Member member) {
        return new MemberResponse(
                member.getId(),
                member.getUsername(),
                member.getNickname(),
                member.getCreatedAt()
        );
    }
}
