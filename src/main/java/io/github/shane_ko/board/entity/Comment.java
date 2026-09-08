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
    @Column(nullable = false)
    private String writer;
    @Column(nullable = false)
    private String content;
    private LocalDateTime createdAt;

    public Comment(String writer, String content, Article article) {
        this.writer = writer;
        this.content = content;
        this.article = article;
        this.createdAt = LocalDateTime.now();
    }

    public void update (String content) {
        if(content != null) this.content = content;
    }
}
