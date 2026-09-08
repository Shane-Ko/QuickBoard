package io.github.shane_ko.board.service;

import io.github.shane_ko.board.dto.request.ArticleUpdateRequest;
import io.github.shane_ko.board.entity.Article;
import io.github.shane_ko.board.dto.request.ArticleCreateRequest;
import io.github.shane_ko.board.entity.Comment;
import io.github.shane_ko.board.exception.ArticleNotFoundException;
import io.github.shane_ko.board.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
@Slf4j
public class ArticleService {

    private final ArticleRepository articleRepository;

    // 연습으로 생성자 직접 넣음
    @Autowired
    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;}



    /*
        * 서비스에서 예외를 만들어 놓고
        * 예외를 하나도 안받고 있었다
        * TODO
        *  예외 받기
     */
    // 쓰기 메서드는 @Transactional로 오버라이드
    @Transactional
    public Article save(ArticleCreateRequest form) {
        // 빈칸 검증 메서드

        // 1. dto -> entity 로 변환 (toEntity())
        return articleRepository.save(form.toEntity());
    }

    //ReadOne
    public Article findOne(Long id) {
        return articleRepository
                .findById(id)
                .orElseThrow(() -> new ArticleNotFoundException(id));
    }

    //ReadAll
    public List<Article> findAll() {
        return articleRepository.findAll();
    }

    @Transactional
    public Article update(Long id, ArticleUpdateRequest dto) {

        /*
        toEntity() 필요없음. 이미 조회된 데이터가 Entity 임
         */
        // 1. target 엔티티 조회
        log.info("target 엔티티 조회");
        Article target = articleRepository.findById(id)
                .orElseThrow(() -> new ArticleNotFoundException(id));

        // 2. 업데이트
        log.info("patch 메서드 실행");
        target.update(dto.getTitle(), dto.getContent());

        // save() 필요없음 - dirty checking
        return target;
    }


    //Delete
    @Transactional
    public void delete(Long id) {
        Article target = articleRepository.findById(id)
                .orElseThrow(() -> new ArticleNotFoundException(id));
        articleRepository.deleteById(target.getId());
        log.info(id + "번 게시글 삭제성공");
    }
}
