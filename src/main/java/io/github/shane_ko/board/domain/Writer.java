package io.github.shane_ko.board.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@NoArgsConstructor
@Getter
public class Writer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nickname;

    public Writer(String nickname) {
        this.nickname = nickname;
    }

    public void updateNickname(String newNickname) {
        this.nickname = newNickname;
    }
}
