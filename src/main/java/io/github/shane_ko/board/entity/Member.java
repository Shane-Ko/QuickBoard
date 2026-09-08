package io.github.shane_ko.board.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Entity
@NoArgsConstructor
@Getter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String username;
    @Column(unique = true, nullable = false)
    private String nickname;
    private String password;
    private LocalDateTime createdAt;

    public Member(String username, String nickname, String password ) {
        this.username = username;
        this.nickname = nickname;
        this.password = password;
        this.createdAt = LocalDateTime.now();
    }

}
