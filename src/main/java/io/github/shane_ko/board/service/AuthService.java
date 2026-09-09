package io.github.shane_ko.board.service;

import io.github.shane_ko.board.dto.request.LoginRequest;
import io.github.shane_ko.board.dto.response.TokenResponse;
import io.github.shane_ko.board.entity.Member;
import io.github.shane_ko.board.exception.InvalidCredentialsException;
import io.github.shane_ko.board.jwt.JwtTokenProvider;
import io.github.shane_ko.board.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthService(MemberRepository memberRepository,
                       PasswordEncoder passwordEncoder,
                       JwtTokenProvider jwtTokenProvider) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }
    public TokenResponse login(LoginRequest dto) {
        // 1. username으로 Member 조회 (없으면 예외)
        Member member = memberRepository.findByUsername(dto.getUsername())
                .orElseThrow(() -> new InvalidCredentialsException());
        // 2. 비밀번호 검증 (틀리면 예외)
        if(!passwordEncoder.matches(dto.getPassword(), member.getPassword())){
            throw new InvalidCredentialsException();
        }
        // 3. 토큰 발급
        String token = jwtTokenProvider.createToken(member.getId());
        // 4. TokenResponse에 담아서 리턴
        return new TokenResponse(token);
    }
}
