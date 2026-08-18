package io.github.shane_ko.board.domain;

import jakarta.persistence.*;
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
    private String title;
    private String content;
    // FK는 Many 쪽에서 가진다. 여기서 Many 는 Article
    @ManyToOne
    private Writer writer;
    private LocalDateTime createdAt;

    public Article(String title,String content, Writer writer) {
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.createdAt = LocalDateTime.now();
    }

    public void updateTitle(String newTitle) {
        this.title = newTitle;
    }

    public void updateContent(String newContent) {
        this.content = newContent;
    }
}
