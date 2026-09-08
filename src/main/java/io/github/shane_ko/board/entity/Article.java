package io.github.shane_ko.board.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
public class Article {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    // Article : Member = N : 1 다대일 단방향 연관관계
    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String title;
    private String content;

    private LocalDateTime createdAt;

    public Article(String title,String content, Member member) {
        this.title = title;
        this.content = content;
        this.member = member;
        this.createdAt = LocalDateTime.now();
    }

    public void update(String title, String content) {
        if (title != null) this.title = title;
        if (content != null) this.content = content;
    }


}
