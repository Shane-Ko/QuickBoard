package io.github.shane_ko.board.service;

import io.github.shane_ko.board.domain.Article;
import io.github.shane_ko.board.dto.ArticleForm;
import io.github.shane_ko.board.repository.ArticleRepository;
import io.github.shane_ko.board.repository.WriterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ArticleService {

    private final ArticleRepository articleRepository;

    // 연습으로 생성자 직접 넣음
    @Autowired
    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;}

    // 쓰기 메서드는 @Transactional로 오버라이드
    // C
    @Transactional
    public Long save(ArticleForm form) {
        // 저장 후 id만 리턴 받는다 (보안상의 이유?)
        // 1. dto -> entity 로 변환 (toEntity())
        return articleRepository.save(form.toEntity()).getId();
    }

    //Read
    public Article findOne(Long id) {
        return articleRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글 입니다 id = " + id));
    }

    //TODO
    public List<Article> findAll() {
        return articleRepository.findAll();
    }


    //TODO 수정해야함
    //Update
    //Dirty Checking
    @Transactional
    public void updateTitle(Long id, String title) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("수정하려는 글이 없습니다."));
        article.updateTitle(title);
    }

    @Transactional
    public void updateContent(Long id, String content) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("수정하려는 글이 없습니다."));
        article.updateContent(content);
    }

    @Transactional
    public void updateBoth(Long id, String title, String content) {
        updateTitle(id, title);
        updateContent(id, content);
    }


    //Delete
    public void delete(Long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("삭제하려는 글이 없습니다 id = " + id));
        articleRepository.deleteById(id);
    }
}
