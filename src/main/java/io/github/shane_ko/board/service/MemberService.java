package io.github.shane_ko.board.service;

import io.github.shane_ko.board.dto.request.MemberCreateRequest;
import io.github.shane_ko.board.entity.Member;
import io.github.shane_ko.board.exception.DuplicateNicknameException;
import io.github.shane_ko.board.exception.DuplicateUsernameException;
import io.github.shane_ko.board.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public MemberService(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    //Creat (회원가입)
    @Transactional
    public Member create(MemberCreateRequest dto) {
        // username 중복 체크
        if (memberRepository.findByUsername(dto.getUsername()).isPresent()) {
            throw new DuplicateUsernameException(dto.getUsername());
        }
        // nickname 중복 체크
        if (memberRepository.findByNickname(dto.getNickname()).isPresent() ) {
            throw new DuplicateNicknameException(dto.getNickname());
        }
        // 1. 비밀번호 인코딩
        String encoded = passwordEncoder.encode(dto.getPassword());

        // 2. 회원 생성
        Member member = new Member(
                dto.getUsername(),
                dto.getNickname(),
                encoded
        );

        // 3. 저장
        return memberRepository.save(member);
    }
}
