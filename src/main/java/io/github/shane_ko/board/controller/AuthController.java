package io.github.shane_ko.board.controller;

import io.github.shane_ko.board.dto.request.LoginRequest;
import io.github.shane_ko.board.dto.response.TokenResponse;
import io.github.shane_ko.board.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/auth/login")
    public ResponseEntity<TokenResponse> login(@Valid @RequestBody LoginRequest dto) {
        TokenResponse response = authService.login(dto);

        return ResponseEntity.ok(response);
    }
}
