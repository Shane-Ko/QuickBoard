package io.github.shane_ko.board.jwt;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private final SecretKey key;
    private final long accessExpiration;

    public JwtTokenProvider(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.token.access-expiration}") long accessExpiration
    ) {
        // secret → SecretKey 변환해서 this.key에 저장
        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        // this.accessExpiration에 저장
        this.accessExpiration = accessExpiration;
    }

    /*
        * 토큰 생성
     */
    public String createToken(Long userId) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + accessExpiration);

        return Jwts.builder()
                .subject(String.valueOf(userId))
                .issuedAt(now)
                .expiration(expiry)
                .signWith(this.key)
                .compact();
    }

    /*
        * 토큰 검사
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parser()                           // 파서 만들기 시작
                    .verifyWith(key)                // 이 Key로 서명 검증을 할것이다
                    .build()                        // 파서 완성
                    .parseSignedClaims(token);      // 실제 파싱 + 검증 실행
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    public Long getUserIdFromToken(String token) {
        return Long.parseLong(
            Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject()
        );
    }
}
