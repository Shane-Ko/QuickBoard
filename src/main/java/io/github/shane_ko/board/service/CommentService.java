package io.github.shane_ko.board.service;

import io.github.shane_ko.board.dto.request.CommentCreateRequest;
import io.github.shane_ko.board.dto.request.CommentUpdateRequest;
import io.github.shane_ko.board.entity.Article;
import io.github.shane_ko.board.entity.Comment;
import io.github.shane_ko.board.exception.ArticleNotFoundException;
import io.github.shane_ko.board.exception.CommentNotFoundException;
import io.github.shane_ko.board.repository.ArticleRepository;
import io.github.shane_ko.board.repository.CommentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@Slf4j
public class CommentService {

    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;

    public CommentService(CommentRepository commentRepository, ArticleRepository articleRepository) {
        this.commentRepository = commentRepository;
        this.articleRepository = articleRepository;
    }

    @Transactional
    public Comment save(Long articleId, CommentCreateRequest dto) {
        // 해당하는 게시글 조회
        Article foundArticle = articleRepository.findById(articleId)
                .orElseThrow(() -> new ArticleNotFoundException(articleId));

        // Comment 생성
        Comment newComment = new Comment(dto.getWriter(), dto.getContent(), foundArticle);

        // 저장
        return commentRepository.save(newComment);
    }

    public List<Comment> index(Long articleId) {
        // 해당하는 게시글 조회
        Article foundArticle = articleRepository.findById(articleId)
                .orElseThrow(() -> new ArticleNotFoundException(articleId));

        return commentRepository.findByArticleId(foundArticle.getId());
    }

    // Update
    @Transactional
    public Comment update(Long id, CommentUpdateRequest dto) {
        Comment target = commentRepository.findById(id)
                .orElseThrow(() -> new CommentNotFoundException(id));
        target.update(dto.getContent());


        return target;
    }


    // Delete
    @Transactional
    public void delete (Long id) {
        Comment target = commentRepository.findById(id)
                .orElseThrow(() -> new CommentNotFoundException(id));
        commentRepository.delete(target);
    }
}
