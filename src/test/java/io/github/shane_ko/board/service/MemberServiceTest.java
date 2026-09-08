package io.github.shane_ko.board.service;

import io.github.shane_ko.board.dto.request.MemberCreateRequest;
import io.github.shane_ko.board.entity.Member;
import io.github.shane_ko.board.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;


    @Test
    @DisplayName("회원가입")
    void signUP_성공() {
        // given
        MemberCreateRequest memberCreateRequest = new MemberCreateRequest(
                "test1", "imTester", "abc123");

        // when
        Member newMember = memberService.create(memberCreateRequest);

        // then
        assertThat(newMember.getUsername()).isEqualTo("test1");
        assertThat(newMember.getNickname()).isEqualTo("imTester");
    }
}
