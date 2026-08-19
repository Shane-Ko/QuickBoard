package io.github.shane_ko.board.dto;

import io.github.shane_ko.board.domain.Article;

public class ArticleForm {

    private String title;
    private String content;
    private String writer;

    public ArticleForm(String title, String content, String writer) {
        this.title = title;
        this.content = content;
        this.writer = writer;
    }

    @Override
    public String toString() {
        return "ArticleForm{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", writer='" + writer + '\'' +
                '}';
    }

    public Article toEntity() {
        return new Article(title, content, writer);
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getWriter() {
        return writer;
    }
}
