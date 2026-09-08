package io.github.shane_ko.board.controller;

import io.github.shane_ko.board.dto.request.MemberCreateRequest;
import io.github.shane_ko.board.dto.response.MemberResponse;
import io.github.shane_ko.board.entity.Member;
import io.github.shane_ko.board.service.MemberService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URL;

@RestController
@Slf4j
public class MemberApiController {

    private final MemberService memberService;

    @Autowired
    public MemberApiController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/api/members")
    public ResponseEntity<MemberResponse> signUp(@Valid @RequestBody MemberCreateRequest dto) {
        log.info("회원가입 요청 받음");
        Member created = memberService.create(dto);
        // Entity -> DTO
        MemberResponse response = MemberResponse.from(created);
        URI location = URI.create("/api/members/" + created.getId());
        log.info("생성된 리소스 위치: {}", location);
        return ResponseEntity.created(location).body(response);
    }
}
