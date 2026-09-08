package io.github.shane_ko.board.repository;

import io.github.shane_ko.board.entity.Comment;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CommentRepository {

    private final EntityManager em;


    //CREATE
    public Comment save (Comment comment) {
        em.persist(comment);
        return comment;
    }

    //READ_ONE
    public Optional<Comment> findById(Long commentId) {
        Comment comment = em.find(Comment.class, commentId);
        return Optional.ofNullable(comment);

    }


    //READ_ALL
    public List<Comment> findAll(Long articleId) {
        return em.createQuery("SELECT c FROM Comment c WHERE c.article.id = :articleId ORDER BY c.createdAt DESC"
        , Comment.class)
                .setParameter("articleId",articleId)    // :articleId 의 값을 채우기 위함
                // SQL injection 방지도 가능
                .getResultList();
    }

    //DELETE
    public void delete (Comment comment) {
        em.remove(comment);
    }
}
