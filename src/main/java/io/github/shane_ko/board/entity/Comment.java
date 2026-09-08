package io.github.shane_ko.board.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    private Article article;

    // Comment : Member = N : 1 다대일 단방향 연관관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(nullable = false)
    private String content;

    private LocalDateTime createdAt;

    public Comment(Member member, String content, Article article) {
        this.member = member;
        this.content = content;
        this.article = article;
        this.createdAt = LocalDateTime.now();
    }

    public void update (String content) {
        if(content != null) this.content = content;
    }
}
